import ch2.FixedLengthFrameDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.util.CharsetUtil;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by nokdu on 2017-05-19.
 */
public class FixedLengthFrameDecoderTest {
    @Test
    public void testFrameDecoded() {

        ByteBuf buf = Unpooled.buffer();
        IntStream.range(0,9).forEach(i->buf.writeByte(i));
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        //Byte Write to Channel
        Assert.assertTrue(channel.writeInbound(input.retain()));
        Assert.assertTrue(channel.finish()); //Finish가 되면, 더 이상 Write를 할 수 없다.


        IntStream.range(0,3).forEach(d->{
            ByteBuf read = channel.readInbound();
            Assert.assertEquals(buf.readSlice(3),read);
            read.release();
        });

        Assert.assertNull(channel.readInbound());
        buf.release();
    }

    @Test
    public void testFrameDecoded2() {
        ByteBuf buf = Unpooled.buffer();
        IntStream.range(0,9).forEach(buf::writeByte);
        ByteBuf input = buf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));
        Assert.assertFalse(channel.writeInbound(input.readBytes(2)));
        Assert.assertTrue(channel.writeInbound(input.readBytes(7)));
        Assert.assertTrue(channel.finish());




    }


}
