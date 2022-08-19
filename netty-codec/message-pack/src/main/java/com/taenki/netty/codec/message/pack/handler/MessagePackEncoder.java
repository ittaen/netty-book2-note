package com.taenki.netty.codec.message.pack.handler;

import com.taenki.netty.codec.vs.java.serial.pojo.UserInfo;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.msgpack.MessagePack;

/**
 * {@link MessagePack} 编码器
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/19 10:33
 */
public class MessagePackEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        MessagePack msgPack = new MessagePack();
        msgPack.register(UserInfo.class);
        // Serialize
        byte[] raw = msgPack.write(o);
        byteBuf.writeBytes(raw);
    }
}
