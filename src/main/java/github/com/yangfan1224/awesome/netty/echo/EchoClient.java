package github.com.yangfan1224.awesome.netty.echo;

import github.com.yangfan1224.awesome.netty.codec.MsgPackDecoder;
import github.com.yangfan1224.awesome.netty.codec.MsgPackEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.net.InetSocketAddress;

public class EchoClient {
    private int port;
    private String host;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public static void main(String []args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            }catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }
        new EchoClient("127.0.0.1", port).run();
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try{
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("frameDecoder",
                                    new LengthFieldBasedFrameDecoder(65535,
                                            0,2,0,2));
                            ch.pipeline().addLast("msgPack decoder", new MsgPackDecoder());
                            ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            ch.pipeline().addLast("msgPack encoder", new MsgPackEncoder());
                            ch.pipeline().addLast(new EchoClientHandler(10));
                        }
                    });

            ChannelFuture future = bootstrap.connect(host, port).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }
}
