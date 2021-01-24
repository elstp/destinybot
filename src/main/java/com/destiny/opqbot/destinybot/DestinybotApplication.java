package com.destiny.opqbot.destinybot;


import com.destiny.opqbot.destinybot.socket.WebSocketEvents;
import com.destiny.opqbot.destinybot.socket.WebSocketIoClient;
import com.destiny.opqbot.destinybot.utils.CreatePicture;
import com.destiny.opqbot.destinybot.utils.Ping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.client.RestTemplate;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;

/**
 * @author admin
 */
@SpringBootApplication
@EnableAsync
@EnableCaching
@EnableRedisHttpSession
public class DestinybotApplication {
    /**
     * redis
     * @return
     */
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        return restTemplate;
    }

    public static void main(String[] args) throws IOException {
      try {

         SpringApplication.run(DestinybotApplication.class, args);
          WebSocketIoClient.initSocket();
          WebSocketEvents.OnCheckLoginQrcode(WebSocketIoClient.socket);
          WebSocketEvents.OnLoginSuccess(WebSocketIoClient.socket);
          WebSocketEvents.OnGroupMsgs(WebSocketIoClient.socket);
          WebSocketEvents.OnFriendMsgs(WebSocketIoClient.socket);
          WebSocketIoClient.connect();

      }catch (Exception e){
          e.printStackTrace();
      }



    }

}
