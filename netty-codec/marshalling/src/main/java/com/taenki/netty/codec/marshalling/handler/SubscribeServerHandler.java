package com.taenki.netty.codec.marshalling.handler;

import com.taenki.netty.codec.marshalling.pojo.SubscribeReq;
import com.taenki.netty.codec.marshalling.pojo.SubscribeResp;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * SubscribeServerHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 16:09
 */
public class SubscribeServerHandler extends ChannelHandlerAdapter {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("Jack".equalsIgnoreCase(req.getUsername())) {
            System.out.println("Service accept client subscribe req : [" + req.toString() + " ]");
            ctx.writeAndFlush(resp(req.getSubReqId()));
        }
    }

    private SubscribeResp resp(int subReqId) {
        SubscribeResp subscribeResp = new SubscribeResp();
        subscribeResp.setSubReqId(subReqId);
        subscribeResp.setRespCode(0);
        subscribeResp.setDesc("succeed");
        return subscribeResp;
    }
}
