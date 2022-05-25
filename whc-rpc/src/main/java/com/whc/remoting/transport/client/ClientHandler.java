package com.whc.remoting.transport.client;

import com.whc.config.CustomThreadPoolConfig;
import com.whc.enums.CompressTypeEnum;
import com.whc.enums.SerializationTypeEnum;
import com.whc.factory.SingletonFactory;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.TimeLine;
import com.whc.remoting.constants.RpcConstants;
import com.whc.remoting.dto.Message;
import com.whc.remoting.dto.Response;
import com.whc.utils.ThreadPoolFactoryUtil;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;

@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler {
    private final UnprocessedRequests unprocessedRequests;
    private final NettyClient nettyRpcClient;

    public ClientHandler() {
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.nettyRpcClient = SingletonFactory.getInstance(NettyClient.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof Message) {
            Message responseMessage = (Message) msg;
            byte messageType = responseMessage.getMessageType();
            if (messageType == RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                log.info("heartBeat [{}]", responseMessage.getData());
                Message rpcMessage = Message.builder()
                        .codec(SerializationTypeEnum.HESSIAN.getCode())
                        .compress(CompressTypeEnum.GZIP.getCode())
                        .messageType(RpcConstants.HEARTBEAT_REQUEST_TYPE).build();
                ctx.writeAndFlush(responseMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            } else if (messageType == RpcConstants.RESPONSE_TYPE) {
                //服务端回传结果
                Response<Object> rpcResponse = (Response<Object>) responseMessage.getData();
                unprocessedRequests.complete(rpcResponse);
                TimeLine timeLine = Monitor.getTimeLine(rpcResponse.getRequestId());
                timeLine.phaseEndAndNext(TimeLine.Phase.WAIT_FOR_RESPONSE);
            }
        }
    }

    /**
     * 处理心跳
     * @param ctx
     * @param event
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object event) throws Exception {
        if (event instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) event).state();
            if (state == IdleState.WRITER_IDLE) {
                log.info("写心跳 [{}]", ctx.channel().remoteAddress());
                Channel channel = nettyRpcClient.getChannel((InetSocketAddress) ctx.channel().remoteAddress());
                Message message = new Message();
                message.setCodec(SerializationTypeEnum.PROTOSTUFF.getCode());
                message.setCompress(CompressTypeEnum.GZIP.getCode());
                message.setMessageType(RpcConstants.HEARTBEAT_REQUEST_TYPE);
                message.setData(RpcConstants.PING);
                channel.writeAndFlush(message).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, event);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof IOException) {
            log.warn("exceptionCaught:客户端[{}]和远程断开连接", ctx.channel().localAddress());
        } else {
            log.error(String.valueOf(cause));
        }
        ctx.pipeline().remove(this);
        ctx.channel().close();
        reconnection(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("channelInactive:{}", ctx.channel().localAddress());
        ctx.pipeline().remove(this);
        ctx.channel().close();
        reconnection(ctx);
    }

    private void reconnection(ChannelHandlerContext ctx) {
        log.info("重新建立连接");
        ExecutorService executorService = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent(new CustomThreadPoolConfig(), "reconnect", true);
        executorService.submit(() -> {
            Channel connect = nettyRpcClient.doConnect(new InetSocketAddress(ctx.channel().remoteAddress().toString().split("/")[0], Integer.parseInt(ctx.channel().remoteAddress().toString().split(":")[1])));
            if (connect.isActive()) {
                log.info("重新连接成功");
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("{}",e.getMessage());
                }
                reconnection(ctx);
            }
        });
    }
}
