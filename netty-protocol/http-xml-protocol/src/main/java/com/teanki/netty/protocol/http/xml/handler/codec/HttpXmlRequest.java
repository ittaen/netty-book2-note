package com.teanki.netty.protocol.http.xml.handler.codec;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * HttpXmlRequest
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 10:47
 */
public class HttpXmlRequest {
    private FullHttpRequest request;
    private Object body;

    public HttpXmlRequest(FullHttpRequest request, Object body) {
        this.request = request;
        this.body = body;
    }

    public final FullHttpRequest getRequest() {
        return request;
    }

    public final void setRequest(FullHttpRequest request) {
        this.request = request;
    }

    public final Object getBody() {
        return body;
    }

    public final void setBody(Object body) {
        this.body = body;
    }
}
