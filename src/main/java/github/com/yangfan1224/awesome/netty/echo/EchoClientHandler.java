package github.com.yangfan1224.awesome.netty.echo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

    private int sendNumber;

    public EchoClientHandler(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    private Person [] personInfo() {
        Person[] persons = new Person[sendNumber];
        Person person = null;

        for (int i = 0; i < sendNumber ; i++) {
            person = new Person("Jemmy===>" + i, 10 + i);
            persons[i] = person;
        }
        return persons;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Person[] persons = personInfo();
        for (Person p: persons) {
            ctx.write(p);
        }
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client receive message:" + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
