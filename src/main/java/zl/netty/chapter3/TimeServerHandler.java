package zl.netty.chapter3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Date;


public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    int counter;

//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        ByteBuf buf = ( ByteBuf ) msg;
//        byte[] req = new byte[buf.readableBytes()];
//        buf.readBytes(req);
//        String body = new String(req, "UTF-8");
//        System.out.println("The time server receive order : " + body);
//        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
//        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
//        ctx.write(resp);
//    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        //ByteBuf in = ( ByteBuf ) msg;
        //byte[] bytes = new byte[in.readableBytes()];
        //in.readBytes(bytes);
        try {
            //String body = new String(bytes,"utf-8")
                    //.substring(0,bytes.length-System.getProperty("line.separator").length());
            String body = (String)msg;
            System.out.println("Server received: " + body+"  ; counter is"+ (++counter));
            String currentTime = "QUERY TIME ORDER".equals(body)
                    ?new Date(System.currentTimeMillis()).toString():"BAD ORDER";
            currentTime = currentTime+System.getProperty("line.separator");
            ByteBuf resp =  Unpooled.copiedBuffer(currentTime.getBytes());
            ctx.writeAndFlush(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
