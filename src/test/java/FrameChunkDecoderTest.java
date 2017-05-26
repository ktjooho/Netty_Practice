import ch2.FrameChunckDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.stream.IntStream;

/**
 * Created by nokdu on 2017-05-19.
 */
public class FrameChunkDecoderTest {
    @Test
    public void testDecoder() {
        ByteBuf buf = Unpooled.buffer();
        IntStream.range(0,9).forEach(i->buf.writeByte(i));
        ByteBuf input = buf.duplicate();

        FrameChunckDecoder decoder = new FrameChunckDecoder(3);
        EmbeddedChannel channel = new EmbeddedChannel(decoder);

        assertTrue(channel.writeInbound(input.readBytes(2)));

        try {
            channel.writeInbound(input.readBytes(4));
            Assert.fail();
        }catch (TooLongFrameException e) {
            System.out.println("Exception");
        }
        assertTrue(channel.writeInbound(input.readBytes(3)));
        assertTrue(channel.finish());

        System.out.println(input.readableBytes());
        System.out.println(buf.readableBytes());

        ByteBuf read = channel.readInbound();
        assertEquals(buf.readSlice(2),read);
        read.release();

        read = channel.readInbound();
        assertEquals(buf.skipBytes(4).readSlice(3),read);

    }
}
