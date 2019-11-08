package github.com.yangfan1224.awesome.rpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class SubReqServerHandler extends SimpleChannelInboundHandler<SubscribeReq> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SubscribeReq msg) throws Exception {
        if ("yangfan".equalsIgnoreCase(msg.getUserName())) {
            System.out.println("Service accept client subscribe req : [" + msg.toString() + "]");
            ctx.writeAndFlush(resp(msg.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqID) {
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("Netty book order succeed, 3 days later");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
