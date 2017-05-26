package ch1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * Created by nokdu on 2017-05-17.
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter{
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Active!!!");
        ctx.writeAndFlush(Unpooled.copiedBuffer("<< Connection is accepted >>",CharsetUtil.UTF_8));
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
       // in.readableBytes()
        //in.writerIndex()
        //in.readerIndex()
        System.out.println("Server Received: " + in.toString(CharsetUtil.UTF_8) + "|| Byte Buffer is " + in.getClass());
        ctx.writeAndFlush(in);


      // ReferenceCountUtil.release(msg);
    }
    /*
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }
    */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
