package github.com.yangfan1224.awesome.netty.echo;

import github.com.yangfan1224.awesome.netty.codec.MsgPackDecoder;
import github.com.yangfan1224.awesome.netty.codec.MsgPackEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
    private int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String [] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try{
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            }
        }

        new EchoServer(port).run();
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        try{
            bootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //首先加入LengthFieldBasedFrameDecoder解码器，解决tcp粘包和拆包的问题 Inbound
                            ch.pipeline().addLast("frameDecoder",
                                    new LengthFieldBasedFrameDecoder(65535, 0,
                                            2,0,2));
                            //加入MessagePackDcoder Inbound
                            ch.pipeline().addLast("msgPack decoder", new MsgPackDecoder());
                            //加入LengthFieldPrepender编码器，解决tcp粘包和拆包的问题 Outbound
                            ch.pipeline().addLast("frameEncoder", new LengthFieldPrepender(2));
                            //加入MessagePackEncoder Outbound
                            ch.pipeline().addLast("msgPack encoder", new MsgPackEncoder());
                            //业务逻辑
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });
            System.out.println("Echo Server start at port : " + port);
            ChannelFuture future = bootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException ex) {

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
