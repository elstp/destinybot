package com.destiny.opqbot.destinybot.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author admin
 */
@Configuration
public class SocketIOConfig {

    private static String host;

    private static Integer reconnectionAttempts;

    private static Integer reconnectionDelay;

    private static Integer timeout;

    @Value("${web-socketio.host}")
    public  void setHost(String host) {
        SocketIOConfig.host = host;
    }

    @Value("${web-socketio.reconnectionAttempts}")
    public  void setReconnectionAttempts(Integer reconnectionAttempts) {
        SocketIOConfig.reconnectionAttempts = reconnectionAttempts;
    }

    @Value("${web-socketio.reconnectionDelay}")
    public  void setReconnectionDelay(Integer reconnectionDelay) {
        SocketIOConfig.reconnectionDelay = reconnectionDelay;
    }
    @Value("${web-socketio.timeout}")
    public  void setTimeout(Integer timeout) {
        SocketIOConfig.timeout = timeout;
    }

    public static String getHost() {
        return host;
    }

    public static Integer getReconnectionAttempts() {
        return reconnectionAttempts;
    }

    public static Integer getReconnectionDelay() {
        return reconnectionDelay;
    }

    public static Integer getTimeout() {
        return timeout;
    }
}
