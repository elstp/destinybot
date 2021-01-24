package com.destiny.opqbot.destinybot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
public class robotConfig {

    private static String qq;
    private static String queryToken;
    private static String url;
    private static String serverList;

    private static String imgPath;

    public static String getImgPath() {
        return imgPath;
    }
    @Value("${Img.imgPath}")
    public  void setImgPath(String imgPath) {
        robotConfig.imgPath = imgPath;
    }

    public static String getServerList() {
        return serverList;
    }

    @Value("${serverList.data}")
    public  void setServerList(String serverList) {
        robotConfig.serverList = serverList;
    }

    @Value("${robot.qq}")
    public void setQq(String qq) {
        robotConfig.qq = qq;
    }

    public static String getQq() {
        return qq;
    }

    @Value("${query.token}")
    public void setQueryToken(String queryToken) {
        robotConfig.queryToken = queryToken;
    }

    public static String getQueryToken() {
        return queryToken;
    }

    @Value("${query.url}")
    public void setUrl(String url) {
        robotConfig.url = url;
    }

    public static String getUrl() {
        return url;
    }
}
