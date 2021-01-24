package com.destiny.opqbot.destinybot.redis;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**@description: 自定义注解，结合AOP实现Redis自动缓存
 * 此注解还可以使用布隆过滤器，对数据库和缓存中都不存在的查询放进过滤器，防止缓存击穿攻击；
 * @author Administrator
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface RedisCacheSave {
    /**key的前缀*/
    String nameSpace() default "";

    /**key*/
    String key();

    /**过期时间*/
    long expire() default -1;

    /**过期时间单位*/
    TimeUnit unit() default TimeUnit.SECONDS;

    /**
     * 是否为查询操作
     * 如果为写入数据库的操作，该值需置为 false
     */
    boolean read() default true;
}
