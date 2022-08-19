package com.taenki.netty.codec.message.pack;

import com.taenki.netty.codec.vs.java.serial.pojo.UserInfo;
import org.msgpack.MessagePack;
import org.msgpack.template.Templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MessagePack示例
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 10:21
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        // Create serialize objects
        List<String> src = new ArrayList<>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");
        MessagePack msgpack = new MessagePack();
        // Serialize
        byte[] raw = msgpack.write(src);
        // Deserialize directly using a template
        List<String> dst1 = msgpack.read(raw, Templates.tList(Templates.TString));
        dst1.forEach(System.out::println);

        // 序列化 POJO
        msgpack.register(UserInfo.class);
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("Jack");
        userInfo.setUserId(1);
        byte[] userRaw = msgpack.write(userInfo);
        UserInfo info = msgpack.read(userRaw, UserInfo.class);
        System.out.println(info);
    }
}
