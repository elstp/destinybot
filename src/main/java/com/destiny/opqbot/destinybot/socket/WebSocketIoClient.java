package com.destiny.opqbot.destinybot.socket;

import java.net.URISyntaxException;

import com.destiny.opqbot.destinybot.config.SocketIOConfig;
import com.destiny.opqbot.destinybot.config.robotConfig;
import com.destiny.opqbot.destinybot.utils.BareBonesBrowserLaunch;
import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * @author admin
 */
public class WebSocketIoClient {
    public static Socket socket;


    /**
     * 连接
     */
    public static void connect(){
        socket.on(Socket.EVENT_CONNECT, objects  -> {
            System.out.println("SUCCESS");
            socket.emit("GetWebConn", robotConfig.getQq());
           // BareBonesBrowserLaunch.openURL(SocketIOConfig.getHost()+"/v1/Login/GetQRcode");
            System.out.println("等待登陆...");
        });
        socket.on(Socket.EVENT_PING, objects  ->  System.out.println("ping"));
        socket.on(Socket.EVENT_CONNECT_TIMEOUT, objects -> System.out.println("client: " + "连接超时"));
        socket.on(Socket.EVENT_CONNECT_ERROR, objects -> System.out.println("client: " + "连接失败"));
        socket.connect();
    }

    /**
     * 初始化设置
     * @return
     */
    public static boolean initSocket()   {
        try {
            IO.Options options = new IO.Options();
            options.transports = new String[]{"websocket"};
            // 重连尝试次数
            options.reconnectionAttempts = SocketIOConfig.getReconnectionAttempts();
            // 失败重连的时间间隔(ms)
            options.reconnectionDelay = SocketIOConfig.getReconnectionDelay();
            // 连接超时时间(ms)
            options.timeout = SocketIOConfig.getTimeout();
            options.forceNew = true;
            socket = IO.socket(SocketIOConfig.getHost(), options);
            return true;
        }catch (Exception URISyntaxException){
            return false;
        }
    }
}
