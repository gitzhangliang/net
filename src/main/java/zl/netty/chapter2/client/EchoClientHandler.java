package zl.netty.chapter2.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * 当ChannelHandler 被添加到ChannelPipeline 时，
 * 它将会被分配一个ChannelHandlerContext，
 * 其代表了 ChannelHandler 和 ChannelPipeline 之间的绑定。虽然这个对象可
 * 以被用于获取底层的 Channel，但是它主要还是被用于写出站数据
 *
 * 在 Netty 中，有两种发送消息的方式。你可以直接写到 Channel 中，
 * 也可以 写到和 ChannelHandler相关联的ChannelHandlerContext对象中。\
 * 前一种方式将会导致消息从ChannelPipeline 的尾端开始流动，
 * 而后者将导致消息从 ChannelPipeline 中的下一个 ChannelHandler 开始流动。
 */
@ChannelHandler.Sharable
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(System.currentTimeMillis()+" client acticve");
        ctx.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!", CharsetUtil.UTF_8));
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        System.out.println(System.currentTimeMillis()+"  Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}