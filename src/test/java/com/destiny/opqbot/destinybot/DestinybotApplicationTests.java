package com.destiny.opqbot.destinybot;

import com.destiny.opqbot.destinybot.enumType.sendMsgType;
import com.destiny.opqbot.destinybot.impl.QueryServerImpl;
import com.destiny.opqbot.destinybot.utils.CreatePicture;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@SpringBootTest
class DestinybotApplicationTests {

    @Test
    void contextLoads() throws IOException {
        System.out.println(sendMsgType.SendFriendPictureInformation.getValue());
        System.out.println(QueryServerImpl.queryArma3ServerInfo("119.188.248.105", "2303", "E044CB40-CE91-4BC1-A1F8-6E6D226143F0"));






    }

}
