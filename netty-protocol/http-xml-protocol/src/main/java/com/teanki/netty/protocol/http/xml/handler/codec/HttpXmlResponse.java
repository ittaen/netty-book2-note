package com.teanki.netty.protocol.http.xml.handler.codec;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * HttpXmlRequest
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 10:47
 */
public class HttpXmlResponse {
    private FullHttpResponse response;
    private Object body;

    public HttpXmlResponse(FullHttpResponse response, Object body) {
        this.response = response;
        this.body = body;
    }

    public final FullHttpResponse getResponse() {
        return response;
    }

    public final void setResponse(FullHttpResponse response) {
        this.response = response;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }
}
