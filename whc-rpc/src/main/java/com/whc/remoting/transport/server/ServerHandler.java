package com.whc.remoting.transport.server;

import com.whc.enums.CompressTypeEnum;
import com.whc.enums.ResponseCodeEnum;
import com.whc.enums.SerializationTypeEnum;
import com.whc.factory.SingletonFactory;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.TimeLine;
import com.whc.invoke.ServerInvoker;
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
    private final ServerInvoker serverInvoker;

    public ServerHandler() {
        this.serverInvoker = SingletonFactory.getInstance(ServerInvoker.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object requestMessage) throws Exception {
        try {
            if (requestMessage instanceof Message) {
                byte messageType = ((Message) requestMessage).getMessageType();
                Message responseMessage = new Message();
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
                    Response<Object> rpcResponse = serverInvoker.handle(rpcRequest);
                    timeLine.phaseEndAndNext(TimeLine.Phase.HANDLE);
                    responseMessage.setMessageType(RpcConstants.RESPONSE_TYPE);
                    if (ctx.channel().isActive() && ctx.channel().isWritable()) {
                        responseMessage.setData(rpcResponse);
                    } else {
                        Response<Object> response = Response.fail(ResponseCodeEnum.FAIL,"");
                        responseMessage.setData(response);
                        log.error("连接失效，请求无法发送");
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
                log.info("心跳检测失败，断开连接");
                ctx.close();
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("服务提供端异常");
        cause.printStackTrace();
        ctx.close();
    }
}

