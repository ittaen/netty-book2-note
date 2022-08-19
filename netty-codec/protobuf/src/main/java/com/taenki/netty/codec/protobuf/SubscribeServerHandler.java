package com.taenki.netty.codec.protobuf;

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
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if ("Jack".equalsIgnoreCase(req.getUsername(0))) {
            System.out.println("Service accept client subscribe req : [" + req.toString() + " ]");
            ctx.writeAndFlush(resp(req.getSubReqId(0)));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqId) {
        return SubscribeRespProto.SubscribeResp.newBuilder()
                .addSubReqId(subReqId)
                .addRespCode(0)
                .addDesc("succeed")
                .build();
    }
}
