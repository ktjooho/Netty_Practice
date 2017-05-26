package ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

/**
 * Created by nokdu on 2017-05-19.
 */
public class EchoProxyServer {

    final int portAsClient = 5050;
    final String hostAsClient = "localhost";
    final int portAsServer = 6500;

    public EchoProxyServer() {
    }

    public static void main(String ... args) {
        System.out.println("ProxyServer");
        new EchoProxyServer().start();
    }
    public void start( ) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(new NioEventLoopGroup(),new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class).childHandler(
                new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectFuture;
                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        System.out.println("Proxy as Server is activated now");
                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.channel(NioSocketChannel.class).handler(
                                new SimpleChannelInboundHandler<ByteBuf>() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        System.out.println("Proxy as Client is activated now");
                                        ctx.writeAndFlush(Unpooled.copiedBuffer("[Proxy AS Client]",CharsetUtil.UTF_8));
                                    }
                                      @Override
                                    protected void channelRead0(ChannelHandlerContext c, ByteBuf msg) throws Exception {
                                        System.out.println("Proxy as Client get Msg : " + msg.toString(CharsetUtil.UTF_8));
                                    }
                                }
                        ).group(ctx.channel().eventLoop())
                        .remoteAddress(hostAsClient,portAsClient).option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000);

                        connectFuture = bootstrap.connect();
                        connectFuture.addListener(f->{
                            if(f.isSuccess()) {
                                System.out.println("Proxy as Client is success to conenct Server");
                            } else {
                                f.cause().printStackTrace();
                                ctx.writeAndFlush(Unpooled.copiedBuffer("서버에 요청이 안 되고 있읍니다.",CharsetUtil.UTF_8));
                            }
                        });
                    }
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        if(connectFuture.isDone()) {
                            System.out.println("Proxy as Server Get Msg :" + msg.toString(CharsetUtil.UTF_8));
                        }
                    }
                });
        ChannelFuture future = bootstrap.bind(portAsServer);
        future.addListener(f-> {
           if(f.isSuccess()) {
               System.out.println("Proxy Server is Binding the port ");
           } else {
               f.cause().printStackTrace();
           }
        });
    }
}
