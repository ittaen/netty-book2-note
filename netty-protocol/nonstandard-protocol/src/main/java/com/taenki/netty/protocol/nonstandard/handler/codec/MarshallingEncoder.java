package com.taenki.netty.protocol.nonstandard.handler.codec;

import com.taenki.netty.protocol.nonstandard.factory.MarshallingCodecFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import org.jboss.marshalling.Marshaller;

import java.io.IOException;

/**
 * MarshallingEncoder
 * <p>
 *
 * @author : Taen
 * @date : 2022/8/21 15:54
 */
@ChannelHandler.Sharable
public class MarshallingEncoder {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    Marshaller marshaller;

    public MarshallingEncoder() throws IOException {
        marshaller = MarshallingCodecFactory.buildMarshalling();
    }

    public void encode(Object msg, ByteBuf out) throws Exception {
        try {
            int lengthPos = out.writerIndex();
            out.writeBytes(LENGTH_PLACEHOLDER);
            ChannelBufferByteOutput output = new ChannelBufferByteOutput(out);
            marshaller.start(output);
            marshaller.writeObject(msg);
            marshaller.finish();
            out.setInt(lengthPos, out.writerIndex() - lengthPos - 4);
        } finally {
            marshaller.close();
        }
    }

}
