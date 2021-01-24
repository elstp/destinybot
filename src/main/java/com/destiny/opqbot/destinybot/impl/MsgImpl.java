package com.destiny.opqbot.destinybot.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.destiny.opqbot.destinybot.config.robotConfig;
import com.destiny.opqbot.destinybot.enumType.sendMsgType;
import com.destiny.opqbot.destinybot.redis.RedisStaticUtils;
import com.destiny.opqbot.destinybot.redis.RedisUtils;
import com.destiny.opqbot.destinybot.utils.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author admin
 */
@Component
public class MsgImpl {
    @Resource
    private StringRedisTemplate stringRedisTemplate;


  private static final Pattern IP_REGEX = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
    private static final Pattern PORT_REGEX = Pattern.compile("-?[1-9]\\d*");

    public static boolean in(String data,String[] serverArr){
        for (String str : serverArr) {
            String[] strArr = str.split(",");
            if (data.equals(strArr[1])) {
                return true;
            }
        }
        return false;
    }

    public static String[] serverInfo(String data,String[] serverArr){
        for (String str : serverArr) {
            String[] strArr = str.split(",");
            if (data.equals(strArr[1])) {
                return new String[] {strArr[2],strArr[3],strArr[0]};
            }
        }
        return null;
    }


    public static boolean queryArma3Server(String data){
        String fromGroupId = "";
        try{

            JSONArray jsonArray = JSONArray.parseArray(data);
            for (Object o: jsonArray) {
                JSONObject json = (JSONObject) o;
                JSONObject tempData = JSON.parseObject(json.getString("CurrentPacket"));
                JSONObject tempData2 = JSON.parseObject(tempData.getString("Data"));
                if ("TextMsg".equals(tempData2.getString("MsgType"))){
                    //消息
                    String content = tempData2.getString("Content");
                    //是否包含指定触发关键词
                    String serverList = robotConfig.getServerList();
                    String[] serverArr = serverList.split("\\|");
                    if (in(content,serverArr)){
                        long start = System.currentTimeMillis();
                        //来源群
                         fromGroupId = tempData2.getString("FromGroupId");
                        //QQ
                        String fromUserId = tempData2.getString("FromUserId");
                      //  String[] strArr = content.split(" ");
                       // if (strArr.length <= 2){ return false; }
                        //正则匹配
                      //  Matcher m = IP_REGEX.matcher(strArr[1]);
                      //  if (!m.find( )){return false;};
                      //   m = PORT_REGEX.matcher(strArr[2]);
                      //  if (!m.find( )){return false;};
                        String[] strArr = serverInfo(content,serverArr);
                        RobotFnc.sendGroupMsg(String.format(sendMsgType.SendGroupTextMessage.getValue(),fromGroupId,"查询中请稍后..."));
                        String s = QueryServerImpl.queryArma3ServerInfo(strArr[0], strArr[1], robotConfig.getQueryToken());
                        if (s == null) {
                            RobotFnc.sendGroupMsg(String.format(sendMsgType.SendGroupTextMessage.getValue(),fromGroupId,"查询服务器出错!请等会再试!"));
                            return false;
                        }
                        JSONObject jsonObject =  JSON.parseObject(JSON.parseObject(s).getString("serverData"));
                        JSONArray jsonArray1 = JSONArray.parseArray(JSON.parseObject(s).getString("playerData"));
                        if ("fail".equals(jsonObject.getString("code"))){
                            RobotFnc.sendGroupMsg(String.format(sendMsgType.SendGroupTextMessage.getValue(),fromGroupId,jsonObject.getString("msg")));
                            return false;
                        }
                        String playerData = "";

                        if (!StringUtils.isEmpty(jsonArray1)){
                            for (int i = 0; i < jsonArray1.size(); i++) {
                                JSONObject tempJson = (JSONObject) jsonArray1.get(i);
                                String online="4294967293".equals(tempJson.getString("Duration"))?"0":tempJson.getString("Duration");
                                playerData += "ID:"+(i+1)+"      名称:"+tempJson.getString("playerName")+"      得分:"+ tempJson.getString("Score")+"      在线:"+online+"|";

                            }

                        }else{
                            playerData = "无在线玩家";
                        }


                      //  RedisUtils redisUtils = new RedisUtils(stringRedisTemplate);
                        CreatePicture CP = new CreatePicture();
                        File sourceFile =  new File(robotConfig.getImgPath());

                        String ping =Ping.getSubString(Ping.ping(strArr[0]),"time ","ms").trim();

                        String s1 = RedisStaticUtils.get("destinybot:timing:" + strArr[2]);
                        JSONObject jsonObject1 = JSON.parseObject(s1);
                        JSONObject jsonObject2 = JSON.parseObject(jsonObject1.getString("data"));
                        String [] harr=new String[10];
                        if (null== jsonObject2) {
                             harr = new String[]{"0","0","0","0","0","0","0","0","0","0"};
                        }

                        harr[0] = jsonObject2.getString("10h")==null?"0":jsonObject2.getString("10h");
                        harr[1] = jsonObject2.getString("9h")==null?"0":jsonObject2.getString("9h");
                        harr[2] = jsonObject2.getString("8h")==null?"0":jsonObject2.getString("8h");
                        harr[3] = jsonObject2.getString("7h")==null?"0":jsonObject2.getString("7h");
                        harr[4] = jsonObject2.getString("6h")==null?"0":jsonObject2.getString("6h");
                        harr[5] = jsonObject2.getString("5h")==null?"0":jsonObject2.getString("5h");
                        harr[6] = jsonObject2.getString("4h")==null?"0":jsonObject2.getString("4h");
                        harr[7] = jsonObject2.getString("3h")==null?"0":jsonObject2.getString("3h");
                        harr[8] = jsonObject2.getString("2h")==null?"0":jsonObject2.getString("2h");
                        harr[9] = jsonObject2.getString("1h")==null?"0":jsonObject2.getString("1h");

                        Integer[] m = new Integer[]{
                                Integer.parseInt(harr[0]),
                                Integer.parseInt(harr[1]),
                                Integer.parseInt(harr[2]),
                                Integer.parseInt(harr[3]),
                                Integer.parseInt(harr[4]),
                                Integer.parseInt(harr[5]),
                                Integer.parseInt(harr[6]),
                                Integer.parseInt(harr[7]),
                                Integer.parseInt(harr[8]),
                                Integer.parseInt(harr[9])

                        };
                        int max = (int) Collections.max(Arrays.asList(m));
                        //平均值
                        int average = Ping.average(m);

                        String[][] textConent = {
                                {
                                        jsonObject.getString("ServerName"),
                                        jsonObject.getString("Players"),
                                        ping,
                                        harr[0]+"人",
                                        harr[1]+"人",
                                        harr[2]+"人",
                                        harr[3]+"人",
                                        harr[4]+"人",
                                        harr[5]+"人",
                                        harr[6]+"人",
                                        harr[7]+"人",
                                        harr[8]+"人",
                                        harr[9]+"人",
                                        jsonObject.getString("MissionName"),
                                        jsonObject.getString("Map"),
                                        jsonObject.getString("ServerAddress"),
                                        max +"人",
                                        average+"人",
                                        playerData
                                }
                        };

                        Object[] objs =CP.generateImage(sourceFile,textConent);
                        BufferedImage image = (BufferedImage) objs[0];
                        File temp = File.createTempFile("destinybotImg", ".png");
                        ImageIO.write(image, "png", temp);
                        String base64 = BaseUtils.GetImageStr( temp.getAbsolutePath());
                        String fileSize = FileSizeUtil.FormetFileSize(temp.length());
                        temp.delete();
                        long finish = System.currentTimeMillis();
                        long timeElapsed = finish - start;
                        String sendData = String.format(sendMsgType.SendGroupBase64PictureInformation.getValue(),fromGroupId,"服务器状态信息已消耗流量:"+fileSize+"  耗时:"+timeElapsed+"毫秒",base64);
                        RobotFnc.sendGroupMsg(sendData);
                        return true;
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                RobotFnc.sendGroupMsg(String.format(sendMsgType.SendGroupTextMessage.getValue(),fromGroupId,"服务器异常稍后请重试!"));
            }catch (Exception e1){ }
        }
        return true;
    }

    /**
     * 网易云点歌
     * @param data
     */
    public static void music163(String data){
        try{
            JSONArray jsonArray = JSONArray.parseArray(data);
            for (Object o: jsonArray) {
                JSONObject json = (JSONObject) o;
                JSONObject tempData = JSON.parseObject(json.getString("CurrentPacket"));
                JSONObject tempData2 = JSON.parseObject(tempData.getString("Data"));
                //来源群
                String fromGroupId = tempData2.getString("FromGroupId");
                if ("XmlMsg".equals(tempData2.getString("MsgType"))){
                    //消息
                    String content = tempData2.getString("Content");

                    if (content.contains("https://music.163.com/song?id=")){
                      String id =   UrlUtils.getSubString(content,"https://music.163.com/song?id=","\\u");
                        String msg = "http://music.163.com/song/media/outer/url?id="+id+".m4a";
                        RobotFnc.sendGroupMsg(String.format(sendMsgType.SendGroupVoiceMessage.getValue(),fromGroupId,msg));
                    }

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
