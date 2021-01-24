package com.destiny.opqbot.destinybot.socket;

import com.destiny.opqbot.destinybot.impl.MsgImpl;
import io.socket.client.Socket;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;

/**事件处理
 * @author admin
 */
@Component
public class WebSocketEvents {


    /**
     * 私聊消息
     * @param socket
     */
    public static void OnFriendMsgs(Socket socket){
        socket.on("OnFriendMsgs", objects  -> {
            String data = Arrays.toString(objects);
            System.out.println(data);
        });
    }

    /**
     * 群消息
     * @param socket
     */
    public static void OnGroupMsgs(Socket socket){
        socket.on("OnGroupMsgs", objects  -> {
            String data = Arrays.toString(objects);

            //查询arma3服务器
            MsgImpl.queryArma3Server(data);
        //    MsgImpl.music163(data);

            System.out.println(data);
        });
    }

    /**
     * 扫码登陆成功
     * @param socket
     */
    public static void OnLoginSuccess(Socket socket){
        socket.on("OnLoginSuccess", objects  -> {
            String data = Arrays.toString(objects);
            System.out.println(data);
        });
    }

    /**
     * 扫码登陆 48未扫描 53已扫码 17 49 过期了
     * @param socket
     */
    public static void OnCheckLoginQrcode(Socket socket){
        socket.on("OnCheckLoginQrcode", objects  -> {
            String data = Arrays.toString(objects);
            System.out.println(data);
        });
    }


}
