package com.taenki.netty.quick.start.codec.demo_01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Date;


/**
 * TimeServerHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/18 18:58
 */
public class TimeServerHandler extends ChannelHandlerAdapter {

    private int counter;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 接收到的msg就是删除回车换行符后的请求消息,不需要额外考虑处理读半包问题,也不需要对请求消息进行编码
        String body = (String) msg;
        //body = body.replace("\n", "");
        System.out.println("The time server receive order : " + body + " ; the counter is : " + ++counter);
        //String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() + "\n" : "BAD ORDER\n";
        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new Date(System.currentTimeMillis()).toString() : "BAD ORDER";
        currentTime = currentTime + System.getProperty("line.separator");
        //System.out.println("server send : " + currentTime);
        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
        ctx.writeAndFlush(resp);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    public static void main(String[] args) {
        System.out.println(("a" + System.getProperty("line.separator") + "a").length());
        System.out.println(System.getProperty("line.separator").getBytes());
        // \r\n
    }
}
