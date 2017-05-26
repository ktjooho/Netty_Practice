package ch2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by nokdu on 2017-05-22.
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("Decode...");
        if(in.readableBytes() >= 4) {
            int data = in.readInt();
            System.out.println("I read ... " + data);

            out.add(data);
        }

    }
}
