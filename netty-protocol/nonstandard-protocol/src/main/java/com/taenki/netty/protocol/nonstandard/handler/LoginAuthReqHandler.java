package com.taenki.netty.protocol.nonstandard.handler;

import com.taenki.netty.protocol.nonstandard.constant.MessageType;
import com.taenki.netty.protocol.nonstandard.pojo.Header;
import com.taenki.netty.protocol.nonstandard.pojo.NettyMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.util.logging.Logger;

/**
 * LoginAuthReqHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 15:48
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

    private static final Logger LOG = Logger.getLogger(LoginAuthReqHandler.class.getName());

    /**
     * Calls {@link ChannelHandlerContext#fireChannelActive()} to forward to the
     * next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(buildLoginReq());
    }

    /**
     * Calls {@link ChannelHandlerContext#fireChannelRead(Object)} to forward to
     * the next {@link ChannelHandler} in the {@link ChannelPipeline}.
     * <p/>
     * Sub-classes may override this method to change behavior.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        NettyMessage message = (NettyMessage) msg;

        // 如果是握手应答消息，需要判断是否认证成功
        if (message.getHeader() != null
                && message.getHeader().getType() == MessageType.LOGIN_RESP
                .value()) {
            byte loginResult = (byte) message.getBody();
            if (loginResult != (byte) 0) {
                // 握手失败，关闭连接
                ctx.close();
            } else {
                LOG.info("Login is ok : " + message);
                ctx.fireChannelRead(msg);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    private NettyMessage buildLoginReq() {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_REQ.value());
        message.setHeader(header);
        return message;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.fireExceptionCaught(cause);
    }

}
