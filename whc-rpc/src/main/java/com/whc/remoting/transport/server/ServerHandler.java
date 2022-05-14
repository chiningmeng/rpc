package com.whc.remoting.transport.server;

import com.whc.enums.CompressTypeEnum;
import com.whc.enums.ResponseCodeEnum;
import com.whc.enums.SerializationTypeEnum;
import com.whc.factory.SingletonFactory;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.TimeLine;
import com.whc.remoting.constants.RpcConstants;
import com.whc.remoting.dto.Message;
import com.whc.remoting.dto.Request;
import com.whc.remoting.dto.Response;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler {
    private final RpcRequestHandler rpcRequestHandler;

    public ServerHandler() {
        this.rpcRequestHandler = SingletonFactory.getInstance(RpcRequestHandler.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object requestMessage) throws Exception {
        try {
            if (requestMessage instanceof Message) {
                log.info("server receive msg: [{}] ", requestMessage);
                byte messageType = ((Message) requestMessage).getMessageType();
                Message responseMessage = new Message();
                //todo HESSIAN
                responseMessage.setCodec(SerializationTypeEnum.HESSIAN.getCode());
                responseMessage.setCompress(CompressTypeEnum.GZIP.getCode());
                if (messageType == RpcConstants.HEARTBEAT_REQUEST_TYPE) {
                    responseMessage.setMessageType(RpcConstants.HEARTBEAT_RESPONSE_TYPE);
                    responseMessage.setData(RpcConstants.PONG);
                } else {
                    Request rpcRequest = (Request) ((Message) requestMessage).getData();

                    TimeLine timeLine = Monitor.getTimeLine(rpcRequest.getRequestId());
                    timeLine.phaseStartWithTimeStamp(System.currentTimeMillis());
                    // 执行服务端对应方法
                    Object result = rpcRequestHandler.handle(rpcRequest);
                    timeLine.phaseEndAndNext(TimeLine.Phase.HANDLE);
                    log.info(String.format("server get result: %s", result.toString()));
                    responseMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                    if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                        Response<Object> rpcResponse = Response.success(result, rpcRequest.getRequestId());
                        responseMessage.setData(rpcResponse);
                    } else {
                        Response<Object> rpcResponse = Response.fail(ResponseCodeEnum.FAIL);
                        responseMessage.setData(rpcResponse);
                        log.error("not writable now, message dropped");
                    }
                }
                ctx.writeAndFlush(responseMessage).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            }
        } finally {
            //确保释放ByteBuf，防止内存泄露
            ReferenceCountUtil.release(requestMessage);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleState state = ((IdleStateEvent) evt).state();
            if (state == IdleState.READER_IDLE) {
                log.info("idle check happen, so close the connection");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server catch exception");
        cause.printStackTrace();
        ctx.close();
    }
}

