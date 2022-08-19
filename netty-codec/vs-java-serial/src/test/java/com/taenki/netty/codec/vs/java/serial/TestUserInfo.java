package com.taenki.netty.codec.vs.java.serial;

import com.taenki.netty.codec.vs.java.serial.pojo.UserInfo;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * 编码测试类
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 8:52
 */
public class TestUserInfo {

    @Test
    public void codecTest() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(100).buildUsername("Welcome to Netty");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(userInfo);
        oos.flush();
        oos.close();
        byte[] b = baos.toByteArray();
        System.out.println("The jdk serializable length is : " + b.length);
        baos.close();
        System.out.println("-----------------------------------------");
        System.out.println("The byte array serializable length is : " + userInfo.codeC().length);
    }

    @Test
    public void codecPerformTest() throws IOException {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserId(100).buildUsername("Welcome to Netty");
        int loop = 100_0000;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(userInfo);
            oos.flush();
            oos.close();
            byte[] b = baos.toByteArray();
            baos.close();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("The jdk serializable cost time is : " + (endTime - startTime) + " ms");
        System.out.println("-----------------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        startTime = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            byte[] b = userInfo.codeC(buffer);
        }
        endTime = System.currentTimeMillis();
        System.out.println("The byte array serializable cost time is : " + (endTime - startTime) + " ms");
    }
}
