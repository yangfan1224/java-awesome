package github.com.yangfan1224.awesome.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TimeClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(TimeClientHandler.class);

    private int counter;
    private byte[] req;

    public TimeClientHandler() {
        req =("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf message = null;
        for (int i = 0; i < 100; i++) {
            message = Unpooled.buffer(req.length);
            message.writeBytes(req);
            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf resp = (ByteBuf) msg;
//        byte[] buf = new byte[resp.readableBytes()];
//        resp.readBytes(buf);
//        String message = new String(buf,"UTF-8");
        String message = (String)msg;
        System.out.println("Now message is :" + message + "; Counter is :" + ++counter);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //释放资源
        logger.warn("unexpected exception from upstream: {}", cause.getMessage());
        ctx.close();
    }
}
