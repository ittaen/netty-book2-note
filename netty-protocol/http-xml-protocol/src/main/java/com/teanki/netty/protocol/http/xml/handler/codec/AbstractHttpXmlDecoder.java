package com.teanki.netty.protocol.http.xml.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;

import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * AbstractHttpXmlDecoder
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 11:14
 */
public abstract class AbstractHttpXmlDecoder<T> extends MessageToMessageDecoder<T> {
    IBindingFactory factory = null;
    StringReader reader = null;
    Class<?> clazz;
    boolean isPrint;
    final static String CHARSET_NAME = "UTF_8";
    final static Charset UTF_8 = StandardCharsets.UTF_8;

    protected AbstractHttpXmlDecoder(Class<?> clazz) {
        this.clazz = clazz;
    }

    public AbstractHttpXmlDecoder(Class<?> clazz, boolean isPrint) {
        this.clazz = clazz;
        this.isPrint = isPrint;
    }

    protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws Exception {
        factory = BindingDirectory.getFactory(clazz);
        String content = body.toString(UTF_8);
        if (isPrint) {
            System.out.println("The body is : " + content);
        }
        reader = new StringReader(content);
        IUnmarshallingContext mctx = factory.createUnmarshallingContext();
        Object result = mctx.unmarshalDocument(reader);
        reader.close();
        reader = null;
        return result;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 释放资源
        if (reader != null) {
            reader.close();
            reader = null;
        }
    }
}
