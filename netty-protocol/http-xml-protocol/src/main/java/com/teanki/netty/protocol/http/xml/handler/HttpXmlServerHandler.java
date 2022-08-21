package com.teanki.netty.protocol.http.xml.handler;

import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlRequest;
import com.teanki.netty.protocol.http.xml.handler.codec.HttpXmlResponse;
import com.teanki.netty.protocol.http.xml.pojo.Address;
import com.teanki.netty.protocol.http.xml.pojo.Order;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;

/**
 * TODO
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 12:35
 */
public class HttpXmlServerHandler extends SimpleChannelInboundHandler<HttpXmlRequest> {
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    private void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, Unpooled.copiedBuffer("失败: " + status.toString() + "\r\n", CharsetUtil.UTF_8));
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpXmlRequest msg) throws Exception {
        HttpRequest request = msg.getRequest();
        Order order = (Order) msg.getBody();
        System.out.println("Http server receive request : " + order);
        doBusiness(order);
        ChannelFuture future = ctx.writeAndFlush(new HttpXmlResponse(null, order));
        if (!HttpHeaders.isKeepAlive(request)) {
            future.addListener(future1 -> ctx.close());
        }
    }

    private void doBusiness(Order order) {
        order.getCustomer().setFirstName("虚");
        order.getCustomer().setLastName("竹");
        List<String> midNames = new ArrayList<>();
        midNames.add("段誉");
        order.getCustomer().setMiddleNames(midNames);
        Address address = order.getBillTo();
        address.setCity("大理");
        address.setState("云南");
        address.setCountry("北宋");
        address.setPostCode("123456");
        order.setBillTo(address);
        order.setShipTo(address);
    }
}
