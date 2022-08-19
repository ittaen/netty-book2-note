package com.taenki.netty.codec.message.pack.demo_01;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * EchoServerHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 7:53
 */
public class EchoServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常，关闭链路
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Server receive the msgPack msg : " + msg);
        ctx.writeAndFlush(msg);
    }
}
