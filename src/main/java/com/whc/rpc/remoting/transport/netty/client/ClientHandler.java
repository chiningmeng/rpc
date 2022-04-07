package com.whc.rpc.remoting.transport.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.Date;

public class ClientHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + ": 客户端写出数据");

        // 1.获取数据
        ByteBuf buffer = getByteBuf(ctx);

        // 2.写数据
        ctx.channel().writeAndFlush(buffer);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){

        // 1.获取数据
        ByteBuf buffer = getByteBuf(ctx);

        // 2.写数据
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "12345 from client ".getBytes(Charset.forName("utf-8"));

        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);

        return buffer;
    }
}
