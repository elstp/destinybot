package com.destiny.opqbot.destinybot.impl;

import com.destiny.opqbot.destinybot.config.robotConfig;
import com.destiny.opqbot.destinybot.utils.HttpClientUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author admin
 */
@Component
public class QueryServerImpl {
   private static Map<String,String> par = new HashMap<>(16);

    /**
     * 查询一个arma3服务器
     * @param ip
     * @param port
     * @param token
     * @return
     */
    public static String queryArma3ServerInfo(String ip,String port,String token){
        par.clear();
        par.put("ip",ip);
        par.put("port",port);
        par.put("token",token);
        HttpClientUtil.HttpClientRequest post = HttpClientUtil.getInstance()
                .setContentType("application/x-www-form-urlencoded")
                .post(robotConfig.getUrl()+"GetServerInfo", par);
        return post.getReponseContent();
    }

}
