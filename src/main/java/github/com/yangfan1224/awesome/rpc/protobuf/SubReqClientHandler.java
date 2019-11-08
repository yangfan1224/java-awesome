package github.com.yangfan1224.awesome.rpc.protobuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;

public class SubReqClientHandler extends SimpleChannelInboundHandler<SubscribeRespProto.SubscribeResp> {

    private SubscribeReqProto.SubscribeReq subReq(int i) {
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(i);
        builder.setUserName("yangfan");
        builder.setProductName("Netty Book");
        List<String> address = new ArrayList<>();
        address.add("Wuhan HongShan");
        address.add("HuBei YiChang");
        builder.addAllAddress(address);
        return builder.build();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SubscribeRespProto.SubscribeResp msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
