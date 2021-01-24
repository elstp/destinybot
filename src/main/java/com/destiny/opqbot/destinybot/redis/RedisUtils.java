package com.destiny.opqbot.destinybot.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author qilong
 */

public class RedisUtils {
    private RedisTemplate redisTemplate;

    private StringRedisTemplate stringRedisTemplate;

    public RedisUtils( ){

    }

    public RedisUtils(RedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public RedisUtils(StringRedisTemplate stringRedisTemplate){
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 删除全部数据中心缓存
     */
    public void deleteAllDataCenterListKeys(){
        Set<String> tokenKey = stringRedisTemplate.keys("server:data:list:dataCenterList:*");
        for (String jsonText: tokenKey) {
            stringRedisTemplate.delete(jsonText);
        }
    }
    public void saveStringData(String K,String V){
        stringRedisTemplate.opsForValue().set(K,V);
    }
    public String getStringData(String K){
        return stringRedisTemplate.opsForValue().get(K);
    }


}

