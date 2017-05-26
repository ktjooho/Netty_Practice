import ch2.ToIntegerDecoder;
import ch2.ToIntegerDecoder2;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.stream.IntStream;

/**
 * Created by nokdu on 2017-05-22.
 */
public class ToIntegerDecoderTest {


    @Test
    public void testDecoder2() {
        final int testSize = 20;
        ByteBuf buf = Unpooled.buffer();
        buf.writeShort(100);
        EmbeddedChannel channel = new EmbeddedChannel(new ToIntegerDecoder2());
        channel.writeInbound(buf);
     //   assertTrue(channel.writeInbound(buf));
      //  assertTrue(channel.finish());




    }

    @Test
    public void testDecode(){
        //Channel
        final int testSize = 20;
        ByteBuf buf = Unpooled.buffer();
        IntStream.range(0,testSize).map(d->d*2).forEach(buf::writeInt);

        EmbeddedChannel channel = new EmbeddedChannel(new ToIntegerDecoder());
        assertTrue(channel.writeInbound(buf));
        assertTrue(channel.finish());
        channel.inboundMessages().stream().forEach(System.out::println);
        assertEquals(testSize,channel.inboundMessages().size());

        IntStream.range(0,testSize).forEach(d->assertEquals(d*2,(int)channel.readInbound()));

    }

}
