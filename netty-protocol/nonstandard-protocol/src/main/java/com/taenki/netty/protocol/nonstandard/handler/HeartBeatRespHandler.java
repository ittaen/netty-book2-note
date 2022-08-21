package com.taenki.netty.protocol.nonstandard.handler;

import com.taenki.netty.protocol.nonstandard.constant.MessageType;
import com.taenki.netty.protocol.nonstandard.pojo.Header;
import com.taenki.netty.protocol.nonstandard.pojo.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * HeartBeatRespHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 15:38
 */
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
    private static final Logger LOG = Logger.getLogger(HeartBeatRespHandler.class.getName());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;
        // 返回心跳应答消息
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.HEARTBEAT_REQ
                .value()) {
            LOG.info("Receive client heart beat message : ---> "
                    + message);
            NettyMessage heartBeat = buildHeatBeat();
            LOG.info("Send heart beat response message to client : ---> "
                    + heartBeat);
            ctx.writeAndFlush(heartBeat);
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildHeatBeat() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.HEARTBEAT_RESP.value());
        message.setHeader(header);
        return message;
    }

}
