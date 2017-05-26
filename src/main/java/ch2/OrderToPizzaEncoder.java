package ch2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by nokdu on 2017-05-22.
 */
public class OrderToPizzaEncoder extends MessageToMessageEncoder<OrderToPizzaEncoder.Order> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Order order, List<Object> out) throws Exception {
        PizzaFactory pizzaFactory = new PizzaFactory();
        out.add(pizzaFactory.createPizza(order));
    }


    public static final class Order {
        private String name;
        private int postalCode;

        public Order(String name, int postalCode) {
            this.name = name;
            this.postalCode = postalCode;
        }

        public String getName() {
            return name;
        }

        public int getPostalCode() {
            return postalCode;
        }
    }

    public enum PostalCode {

        SEOUL(1),
        DAEGU(2),
        DAEJEON(3),
        PUSAN(4);

        private int val;

        PostalCode(int i) {
            val = i;
        }
        public int getCode() {
            return val;
        }
    }

    public static final class Pizza {
        private String name;
        private PostalCode store;

        public Pizza(String name, PostalCode store) {
            this.name = name;
            this.store = store;
        }

        public String getName() {
            return name;
        }

        public PostalCode getStore() {
            return store;
        }
    }

    public static final class PizzaFactory {
        public  Pizza createPizza(Order order) {

            if(order.getName().startsWith("Cheesy")) {
                return new Pizza("Cheese", PostalCode.DAEGU);
            }
            return new Pizza("Normal",PostalCode.SEOUL);
        }
    }
}
