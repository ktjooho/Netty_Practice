import ch2.AbsIntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.stream.IntStream;

/**
 * Created by nokdu on 2017-05-19.
 */
public class AbsIntegerEncoderTest {

    @Test
    public void testEncoder( ) {
        ByteBuf buf = Unpooled.buffer();
        IntStream.rangeClosed(1,10).map(i->i*-1).forEach(buf::writeInt);

        EmbeddedChannel channel = new EmbeddedChannel(new AbsIntegerEncoder());
        assertTrue(channel.writeOutbound(buf));
        assertTrue(channel.finish());

        IntStream.rangeClosed(1,10).peek(System.out::println).
                forEach(i->assertEquals(i,(int)channel.readOutbound()));




    }
}
