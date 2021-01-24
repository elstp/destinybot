package com.destiny.opqbot.destinybot.redis;

import java.lang.annotation.*;

/**
 * 自定义注解，结合AOP实现Redis自动缓存
 * @author Administrator
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface RedisCacheRemove {
    /**key的前缀*/
    String nameSpace() default "";
    String key() default "";
}
