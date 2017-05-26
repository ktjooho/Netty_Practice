package ch1;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;

import java.net.SocketAddress;

/**
 * Created by nokdu on 2017-05-18.
 */
@ChannelHandler.Sharable
public class EchoClientOutBoundHandler extends ChannelOutboundHandlerAdapter {


    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        System.out.println("Remote Address : " + remoteAddress );
        System.out.println("Local Address : " + localAddress );

        ctx.writeAndFlush(Unpooled.copiedBuffer("Math And Couch",CharsetUtil.UTF_8));
        ctx.fireChannelActive();

        promise.addListener((ChannelFutureListener)f->{
            System.out.println("Promise...");
            if(!f.isSuccess()) {

                f.cause().printStackTrace();
                f.channel().close();
            }
        });
        //super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.disconnect(ctx, promise);
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
    }

    @Override
    public void deregister(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.deregister(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("MSG : " + msg.toString());
        ctx.writeAndFlush(Unpooled.copiedBuffer("We are screaming in Color.. ", CharsetUtil.UTF_8));
        promise.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                System.out.println("Writing ... msg is completed");
            }
        });
       // promise.setSuccess();
        ctx.fireChannelRead(msg);
      //  super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
