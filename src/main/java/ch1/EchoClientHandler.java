package ch1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Created by nokdu on 2017-05-17.
 */

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] b = new byte[msg.readableBytes()];
        ByteBufAllocator bba = ctx.alloc();
        System.out.println("할당자 : " + bba.getClass());
        msg.getBytes(0, b, 0, msg.readableBytes());
        //ctx.fireChannelActive()
        String s = new String(b);
        System.out.println("Client received: " + s + "Bt");
        ctx.fireChannelRead(msg.retain());


    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //ctx.alloc().
        System.out.println("Channel is activated");
        ctx.writeAndFlush(Unpooled.copiedBuffer("|Active|", CharsetUtil.UTF_8));
        //ctx.channel().eventLoop().scheduleWithFixedDelay(()->System.out.println("gg"),1000, TimeUnit.MILLISECONDS);


        ctx.channel().eventLoop().scheduleAtFixedRate(()->System.out.println("Rate :" + new Timestamp(System.currentTimeMillis())),1000,2000,TimeUnit.MILLISECONDS);
        ctx.channel().eventLoop().scheduleWithFixedDelay(()->System.out.println("Delay :" +  new Timestamp(System.currentTimeMillis())),1500,2000,TimeUnit.MILLISECONDS);

       // ctx.fireChannelRead(Unpooled.copiedBuffer("Check",CharsetUtil.UTF_8));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Channel is registered");
        //super.channelRegistered(ctx);
        ctx.channel().write(Unpooled.copiedBuffer("|Registered|",CharsetUtil.UTF_8));

    }
}
