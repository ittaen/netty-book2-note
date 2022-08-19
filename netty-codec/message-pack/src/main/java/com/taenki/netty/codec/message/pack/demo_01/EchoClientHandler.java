package com.taenki.netty.codec.message.pack.demo_01;

import com.taenki.netty.codec.vs.java.serial.pojo.UserInfo;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * EchoClientHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 8:01
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    private final int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        UserInfo[] infos = userInfo();
        //Stream.of(infos).forEach(ctx::write);
        for (UserInfo userInfo : infos) {
            ctx.write(userInfo);
        }
        ctx.flush();
    }

    private UserInfo[] userInfo() {
        UserInfo[] infos = new UserInfo[sendNumber];
        UserInfo userInfo;
        for (int i = 0; i < sendNumber; i++) {
            userInfo = new UserInfo();
            userInfo.setUsername("ABCDEFG ----> " + i);
            userInfo.setUserId(i);
            infos[i] = userInfo;
        }
        return infos;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive the msgPack msg : " + msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
