package com.taenki.netty.codec.marshalling.handler;

import com.taenki.netty.codec.marshalling.pojo.SubscribeReq;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * SubscribeClientHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 16:33
 */
public class SubscribeClientHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(subscribeReq(i));
        }
        ctx.flush();
    }

    private SubscribeReq subscribeReq(int subReqId) {
        SubscribeReq subscribeReq = new SubscribeReq();
        subscribeReq.setSubReqId(subReqId);
        subscribeReq.setUsername("Jack");
        subscribeReq.setProductName("Netty Book");
        subscribeReq.setAddress("BeiJing");
        return subscribeReq;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [ " + msg + " ]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
