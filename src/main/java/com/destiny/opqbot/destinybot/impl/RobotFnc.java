package com.destiny.opqbot.destinybot.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.destiny.opqbot.destinybot.config.SocketIOConfig;
import com.destiny.opqbot.destinybot.config.robotConfig;
import com.destiny.opqbot.destinybot.enumType.sendMsgType;
import com.destiny.opqbot.destinybot.utils.HttpClientUtil;

/**
 * @author admin
 */
public class RobotFnc {

    /**
     * 发送消息
     * @param data
     * @return
     * @throws Exception
     */
    public static void sendGroupMsg(String data) throws Exception {
        System.out.println(data.length());
        String pr = "/v1/LuaApiCaller?qq="+ robotConfig.getQq()+"&funcname=SendMsg&timeout=10";
       HttpClientUtil.getInstance().setContentType("application/json").post(SocketIOConfig.getHost() + pr, data);
    }
}
