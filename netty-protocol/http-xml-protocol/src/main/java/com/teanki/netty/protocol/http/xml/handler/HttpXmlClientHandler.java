package com.teanki.netty.protocol.http.xml.handler;

import com.teanki.netty.protocol.http.xml.factory.OrderFactory;
import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlRequest;
import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * HttpXmlClientHandler
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 12:25
 */
public class HttpXmlClientHandler extends SimpleChannelInboundHandler<HttpXmlResponse> {

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlResponse msg) throws Exception {
        System.out.println("The client receive response of http header is : " + msg.getResponse().headers().names());
        System.out.println("Then client receive response of http body is : " + msg.getResponse());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        HttpXmlRequest request = new HttpXmlRequest(null, OrderFactory.create(123));
        ctx.writeAndFlush(request);
    }
}
