package com.destiny.opqbot.destinybot.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 */

@Aspect
@Component

public class RedisCacheAspect {
    @Resource
    private RedisHandler handler;
    private static Logger log = LoggerFactory.getLogger(RedisCacheAspect.class);

    @Pointcut(value = "@annotation(com.destiny.opqbot.destinybot.redis.RedisCacheSave)")
    public void saveCache() {
    }

    @Pointcut(value = "@annotation(com.destiny.opqbot.destinybot.redis.RedisCacheRemove)")
    public void removeCache() {
    }

    // 在使用RedisCacheSave注解的地方织入此切点
    @Around(value = "saveCache()")
    private Object saveCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        log.info("<======拦截到saveCache方法:{}.{}======>" ,
                proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName());
        // 获取切入的方法对象
        // 这个m是代理对象的，没有包含注解
        Method m = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        // this()返回代理对象，target()返回目标对象，目标对象反射获取的method对象才包含注解
        Method methodWithAnnotations = proceedingJoinPoint.getTarget().getClass().getDeclaredMethod(
                proceedingJoinPoint.getSignature().getName(), m.getParameterTypes());

        Object result;
        // 根据目标方法对象获取注解对象
        RedisCacheSave annotation = methodWithAnnotations.getDeclaredAnnotation(RedisCacheSave.class);
        // 解析key
        String key = parseKey(methodWithAnnotations, proceedingJoinPoint.getArgs(), annotation.key(), annotation.nameSpace());
        // 注解的属性本质是注解里的定义的方法
        //Method methodOfAnnotation = a.getClass().getMethod("key");
        // 注解的值本质是注解里的定义的方法返回值
        //String key = (String) methodOfAnnotation.invoke(a);
        // 到redis中获取缓存
        log.info("<====== 通过key：{}从redis中查询 ======>", key);
        String cache = handler.getCache(key);
        if (cache == null) {
            log.info("<====== Redis 中不存在该记录，从数据库查找 ======>");
            // 若不存在，则到数据库中去获取
            result = proceedingJoinPoint.proceed();
            if (result != null) {
                // 从数据库获取后存入redis, 若有指定过期时间，则设置
                long expireTime = annotation.expire();
                if (expireTime != -1) {
                    handler.saveCache(key, result, expireTime, annotation.unit());
                } else {
                    handler.saveCache(key, result);
                }
            }
            return result;
        } else {
            return deSerialize(m, cache);
        }
    }

    private Object deSerialize(Method m, String cache) {
        Class returnTypeClass = m.getReturnType();
        log.info("从缓存中获取数据：{}，返回类型为：{}" , cache, returnTypeClass);
        Object object = null;
        Type returnType = m.getGenericReturnType();
        if(returnType instanceof ParameterizedType){
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            for(Type typeArgument : typeArguments){
                Class typeArgClass = (Class) typeArgument;
                log.info("<======获取到泛型:{}" , typeArgClass.getName());
                object = JSON.parseObject(cache, typeArgClass);
            }
        }else {
            object = JSON.parseObject(cache, returnTypeClass);
        }
        return object;
    }

    // 在使用RedisCacheSave注解的地方织入此切点
    @Around(value = "removeCache()")
    private Object removeCache(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("<======拦截到saveCache方法:{}.{}======>" ,
                proceedingJoinPoint.getTarget().getClass().getName(), proceedingJoinPoint.getSignature().getName());
        // 获取切入的方法对象
        // 这个m是代理对象的，没有包含注解
        Method m = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        // this()返回代理对象，target()返回目标对象，目标对象反射获取的method对象才包含注解
        Method methodWithAnnotations = proceedingJoinPoint.getTarget().getClass().getDeclaredMethod(
                proceedingJoinPoint.getSignature().getName(), m.getParameterTypes());
        Object[] args = proceedingJoinPoint.getArgs();
        Object result;
        result = proceedingJoinPoint.proceed(args);
        RedisCacheRemove annotation = methodWithAnnotations.getAnnotation(RedisCacheRemove.class);
        String key = parseKey(methodWithAnnotations, proceedingJoinPoint.getArgs(), annotation.key(),
                annotation.nameSpace());
        handler.removeCache(key);
        return result;
    }


    //解析springEL表达式
    private String parseKey(Method method, Object[] argValues, String keyEl, String nameSpace) {
        //创建解析器
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(keyEl);
        EvaluationContext context = new StandardEvaluationContext(); // 参数
        // 添加参数
        DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
        String[] parameterNames = discover.getParameterNames(method);
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], argValues[i]);
        }
        // 解析
        Object re =  expression.getValue(context);
        return  nameSpace +":"+ method.getName()+":" +re;
    }


    @Component
    class RedisHandler {
        @Resource
        StringRedisTemplate cache;
        @PostConstruct
        StringRedisTemplate init() {
            GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();
            cache.setDefaultSerializer(serializer);
            return cache;
        }
        <T> void saveCache(String key, T t, long expireTime, TimeUnit unit) {
            String value = JSON.toJSONString(t);
            log.info("<====== 存入Redis 数据：{}", value);
            cache.opsForValue().set(key, value, expireTime, unit);
        }
        <T> void saveCache(String key, T t) {
            String value = JSON.toJSONString(t, SerializerFeature.WRITE_MAP_NULL_FEATURES);
            cache.opsForValue().set(key, value);
        }
        void removeCache(String key) {
            cache.delete(key);
        }

        String getCache(String key) {
            return cache.opsForValue().get(key);
        }

    }
}
