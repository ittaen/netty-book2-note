package com.taenki.netty.protocol.nonstandard.pojo;

/**
 * NettyMessage
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 15:44
 */
public class NettyMessage {
    private Header header;

    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
