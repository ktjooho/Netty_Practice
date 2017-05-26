import ch2.OrderToPizzaEncoder;
import io.netty.channel.Channel;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by nokdu on 2017-05-22.
 */
public class OrderToPizzaEncoderTest {

    //Encode -> Outbound
    //Decode -> InBound
    @Test
    public void testEncodeTest() {
        OrderToPizzaEncoder.Order order = new OrderToPizzaEncoder.Order("Cheese", OrderToPizzaEncoder.PostalCode.SEOUL.getCode());
        OrderToPizzaEncoder.PizzaFactory pizzaFactory = new OrderToPizzaEncoder.PizzaFactory();
        EmbeddedChannel channel = new EmbeddedChannel(new OrderToPizzaEncoder());
        assertTrue(channel.writeOutbound(order));
        assertTrue(channel.finish());

        OrderToPizzaEncoder.Pizza pizza = null;
        System.out.println(channel.outboundMessages().element().getClass().getName());
        pizza = (OrderToPizzaEncoder.Pizza)channel.readOutbound();

        System.out.println(pizza.getName());
    }
}
