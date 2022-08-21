package com.teanki.netty.protocol.http.xml.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.util.List;

/**
 * HttpXmlResponseEncoder
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 11:33
 */
public class HttpXmlResponseEncoder extends AbstractHttpXmlEncoder<HttpXmlResponse> {
    @Override
    protected void encode(ChannelHandlerContext ctx, HttpXmlResponse msg, List<Object> out) throws Exception {
        ByteBuf body = encode0(ctx, msg.getBody());
        FullHttpResponse response = msg.getResponse();
        if (response == null) {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, body);
        } else {
            response = new DefaultFullHttpResponse(msg.getResponse().getProtocolVersion(), msg.getResponse().getStatus(), body);
        }
        response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/xml");
        HttpHeaders.setContentLength(response, body.readableBytes());
        out.add(response);
    }
}
