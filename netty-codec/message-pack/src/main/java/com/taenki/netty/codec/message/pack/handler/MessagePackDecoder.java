package com.taenki.netty.codec.message.pack.handler;

import com.taenki.netty.codec.vs.java.serial.pojo.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

/**
 * {@link MessagePack} 解码器
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 10:39
 */
public class MessagePackDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final byte[] array;
        final int length = byteBuf.readableBytes();
        array = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), array, 0, length);
        MessagePack msgPack = new MessagePack();
        msgPack.register(UserInfo.class);
        list.add(msgPack.read(array, UserInfo.class));
    }
}
