package com.destiny.opqbot.destinybot.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.destiny.opqbot.destinybot.redis.RedisCustomCacheWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @author skycity
 * @create 2019-10-22 19:23
 */
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;



    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        FastJsonRedisSerializer redisSerializer = new FastJsonRedisSerializer(Object.class);
        template.setValueSerializer(redisSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(redisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Override
    @Bean
    public CacheManager cacheManager() {
        RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
        //上面实现的缓存读写
        RedisCustomCacheWriter cachaWriterCustomer = new RedisCustomCacheWriter(connectionFactory);
        CacheManager cm = new RedisCacheManager(cachaWriterCustomer,redisCacheConfiguration());
        return cm;
    }



    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())).entryTtl(Duration.ofSeconds(30));
        return configuration;
    }
}
