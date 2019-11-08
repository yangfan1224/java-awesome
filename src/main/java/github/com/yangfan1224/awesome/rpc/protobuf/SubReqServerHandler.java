package github.com.yangfan1224.awesome.rpc.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SubReqServerHandler extends SimpleChannelInboundHandler<SubscribeReqProto.SubscribeReq> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SubscribeReqProto.SubscribeReq msg) throws Exception {
        if ("yangfan".equalsIgnoreCase(msg.getUserName())) {
            System.out.println("Service accept client subscribe req : [" + msg.toString() + "]");
            ctx.writeAndFlush(resp(msg.getSubReqID()));
        }
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID) {
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed, 3 days later");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
