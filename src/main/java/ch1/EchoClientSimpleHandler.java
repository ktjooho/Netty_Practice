package ch1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by nokdu on 2017-05-18.
 */
@ChannelHandler.Sharable
public class EchoClientSimpleHandler extends SimpleChannelInboundHandler<ByteBuf> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ctx.write(Unpooled.copiedBuffer("Shout out all you kids, bomb botter better models",CharsetUtil.UTF_8));
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Simple Handler is activated");
        ctx.writeAndFlush(Unpooled.copiedBuffer("I am so simple handler", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Simple Handler is registered");
        ctx.channel().write(Unpooled.copiedBuffer("I'm just registered",CharsetUtil.UTF_8));
    }




}
