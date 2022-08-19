package com.taenki.netty.codec.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

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

    private SubscribeReqProto.SubscribeReq subscribeReq(int subReqId) {
        List<String> address = new ArrayList<>();
        address.add("BeiJing");
        address.add("ShenZheng");
        address.add("GuangZhou");
        return SubscribeReqProto.SubscribeReq.newBuilder()
                .addSubReqId(subReqId)
                .addUsername("Jack")
                .addProductName("Netty Book")
                .addAllAddress(address)
                .build();
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
