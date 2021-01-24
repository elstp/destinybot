package com.destiny.opqbot.destinybot.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.destiny.opqbot.destinybot.config.robotConfig;
import com.destiny.opqbot.destinybot.impl.QueryServerImpl;
import com.destiny.opqbot.destinybot.redis.RedisUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.Arrays;

@Component
@EnableAsync
@EnableScheduling
public class Timing {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Async
    @Scheduled(initialDelay = 100,fixedRate = 3600000 )
    public void oneHour() {
        try{
            System.out.println("定时器1运行...");
            saveData("1h");
        }catch (Exception e){
            oneHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 7200000 )
    public void twoHour() {
        try{
            System.out.println("定时器2运行...");
            saveData("2h");
        }catch (Exception e){
            twoHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 10800000  )
    public void threeHour() {
        try{
            System.out.println("定时器3运行...");
            saveData("3h");
        }catch (Exception e){
            threeHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 14400000   )
    public void fourHour() {
        try{
            System.out.println("定时器4运行...");
            saveData("4h");
        }catch (Exception e){
            fourHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 18000000    )
    public void fiveHour() {
        try{
            System.out.println("定时器5运行...");
            saveData("5h");
        }catch (Exception e){
            fiveHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 21600000     )
    public void sixHour() {
        try{
            System.out.println("定时器6运行...");
            saveData("6h");
        }catch (Exception e){
            sixHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 25200000      )
    public void sevenHour() {
        try{
            System.out.println("定时器7运行...");
            saveData("7h");
        }catch (Exception e){
            sevenHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 28800000       )
    public void eightHour() {
        try{
            System.out.println("定时器8运行...");
            saveData("8h");
        }catch (Exception e){
            eightHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 32400000        )
    public void nineHour() {
        try{
            System.out.println("定时器9运行...");
            saveData("9h");
        }catch (Exception e){
            nineHour();
        }
    }
    @Async
    @Scheduled(initialDelay = 100,fixedRate = 36000000         )
    public void tenHour() {
        try{
            System.out.println("定时器10运行...");
            saveData("10h");
        }catch (Exception e){
            tenHour();
        }
    }


    @Async
    public void saveData(String h) throws Exception {
        String serverList = robotConfig.getServerList();
        String[] serverArr = serverList.split("\\|");
        String sum = "0";
        String s;
        JSONObject jsonObject = new JSONObject();

        for (String str : serverArr) {
            String[] strArr = str.split(",");
                Thread.sleep(5000);
                s = QueryServerImpl.queryArma3ServerInfo(strArr[2], strArr[3], robotConfig.getQueryToken());
                jsonObject = JSONObject.parseObject(s);
                if ( null == jsonObject) {
                    continue;
                }
            if ("fail".equals(jsonObject.getString("code")) ) {
                continue;
            }
            JSONObject serverData = JSON.parseObject(jsonObject.getString("serverData"));
            serverData.getInteger("Players");
            RedisUtils redisUtils = new RedisUtils(stringRedisTemplate);
            String stringData = redisUtils.getStringData("destinybot:timing:" + strArr[0]);
            JSONObject jsonSave = new JSONObject();
            JSONObject jsonServerInfo = new JSONObject();

            if (!StringUtils.isEmpty(stringData)) {
                jsonSave = JSON.parseObject(stringData);
                jsonServerInfo = JSON.parseObject(jsonSave.getString("data"));
            }

            jsonServerInfo.put(h, String.valueOf(serverData.getInteger("Players")));
            jsonSave.put("data", jsonServerInfo);
            redisUtils.saveStringData("destinybot:timing:" + strArr[0], jsonSave.toJSONString());

        }
    }
}