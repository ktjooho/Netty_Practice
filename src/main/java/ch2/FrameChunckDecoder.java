package ch2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * Created by nokdu on 2017-05-19.
 */
public class FrameChunckDecoder extends MessageToMessageDecoder<ByteBuf> {
    private final int maxFrameSize;

    public FrameChunckDecoder(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        int readableBytes = msg.readableBytes();
        if(readableBytes > maxFrameSize) {
            msg.clear();
            throw new TooLongFrameException("에러 발생!!!");
        }
        ByteBuf buf = msg.readBytes(readableBytes);
        out.add(buf);
    }


}
