package ch1;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * Created by nokdu on 2017-05-17.
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    private void haveFunWithByteBuf( ) {
        ByteBuf b = Unpooled.copiedBuffer("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
        byte[] bs = "abcdefgh".getBytes();
        ByteBuf c = Unpooled.buffer(35);
        ByteBuf d = Unpooled.wrappedBuffer(bs);

        bs[0] = 'Y';
        System.out.println(d.toString(CharsetUtil.UTF_8));

        System.out.println("B's Reading Index : " + b.readerIndex());
        System.out.println("B's Write Index : " + b.writerIndex());
        System.out.println("B's Capacity:" + b.array().length);
        b.array()[0] = 'X';
        byte x = 1;
        //b.forEachByte()
        //ByteProcessor bp = new ByteProcessor.IndexOfProcessor(x);

        ///b.readBytes(c); //B의 데이터를 C 에서 읽어들임.
        //
        c.writeByte(2);
        c.writeByte(1);
        short y = c.readShort();
        //  c.readShort();
        //  System.out.println("Y :  " + y);
        System.out.println("C's reader Index : " + c.readerIndex());
        System.out.println("C's writer Index : " + c.writerIndex());


        c.writeBytes(b,10); //C의 버퍼에 B 버퍼 데이터를 READ해서  WRITE를 한다.
        System.out.println("Before Discard B's Reading Index : " + b.readerIndex());
        System.out.println("Before Discard B's Write Index : " + b.writerIndex());
        System.out.println("B Content : " + b.toString(CharsetUtil.UTF_8));
        //ByteBuf discard = b.clear();
        //System.out.println("Discard contents : " + discard.toString(CharsetUtil.UTF_8) + "," + b.toString(CharsetUtil.UTF_8));
//        b.discardSomeReadBytes();

        System.out.println("C's reader Index : " + c.readerIndex());
        System.out.println("C's writer Index : " + c.writerIndex());
        System.out.println("B's Reading Index : " + b.readerIndex());
        System.out.println("B's Write Index : " + b.writerIndex());
        //   c.readBytes(b); //C의 버퍼를 Read해서 B로 보낸다.
        //b.readBytes(5);
        // b.readBytes(c,5);
        //  c.readBytes(b,5);

        System.out.println("After C's reader Index : " + c.readerIndex());
        System.out.println("After C's writer Index : " + c.writerIndex());


        System.out.println("C : " + c.toString(CharsetUtil.UTF_8));
        System.out.println("B : " + b.toString(CharsetUtil.UTF_8));
    }

    public static void main(String ...args) throws Exception {
        new EchoClient("localhost",5050).start();
    }
    public void start() throws Exception {

        //EventLoop - Channel
        //
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                  //  .addLast(new EchoClientOutBoundHandler());
                                  // .addLast(new EchoClientHandler());
                                    .addLast(new EchoClientSimpleHandler());

                        }
                    });


            ChannelFuture f = b.connect();

            f.sync();
            f.channel().writeAndFlush(Unpooled.copiedBuffer("Test",CharsetUtil.UTF_8)).addListener(x->{
                if(!f.isSuccess()) {
                    f.cause().printStackTrace();
                }
            });


            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }



    }

}
