package com.whc.remoting.transport.client;

import com.whc.enums.CompressTypeEnum;
import com.whc.enums.SerializationTypeEnum;
import com.whc.factory.SingletonFactory;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.TimeLine;
import com.whc.remoting.constants.RpcConstants;
import com.whc.remoting.dto.Message;
import com.whc.remoting.dto.Response;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

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
        log.info("client receive msg: [{}]", msg);
        if (msg instanceof Message) {
            Message responseMessage = (Message) msg;
            byte messageType = responseMessage.getMessageType();
            if (messageType == RpcConstants.HEARTBEAT_RESPONSE_TYPE) {
                log.info("heartBeat [{}]", responseMessage.getData());
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
                log.info("write idle happened at [{}]", ctx.channel().remoteAddress());
                Channel channel = nettyRpcClient.getChannel((InetSocketAddress) ctx.channel().remoteAddress());
                Message rpcMessage = new Message();
                //todo PROTOSTUFF
                rpcMessage.setCodec(SerializationTypeEnum.PROTOSTUFF.getCode());
                rpcMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                rpcMessage.setMessageType(RpcConstants.HEARTBEAT_REQUEST_TYPE);
                rpcMessage.setData(RpcConstants.PING);
                channel.writeAndFlush(rpcMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } else {
            super.userEventTriggered(ctx, event);
        }
    }

    /**
     * Called when an exception occurs in processing a client message
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("client catch exception：", cause);
        cause.printStackTrace();
        ctx.close();
    }

}
