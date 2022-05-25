package com.whc.remoting.transport.client;

import com.whc.enums.CompressTypeEnum;
import com.whc.enums.SerializationTypeEnum;
import com.whc.extensions.ExtensionLoader;
import com.whc.factory.SingletonFactory;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.TimeLine;
import com.whc.registry.ServiceDiscovery;
import com.whc.remoting.constants.RpcConstants;
import com.whc.remoting.dto.Message;
import com.whc.remoting.dto.Request;
import com.whc.remoting.dto.Response;
import com.whc.remoting.transport.RequestTransport;
import com.whc.remoting.transport.codec.MessageDecoder;
import com.whc.remoting.transport.codec.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public final class NettyClient implements RequestTransport {
    private final ServiceDiscovery serviceDiscovery;
    private final UnprocessedRequests unprocessedRequests;
    private final ChannelProvider channelProvider;
    private final Bootstrap bootstrap;
    private final EventLoopGroup eventLoopGroup;

    public NettyClient() {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new IdleStateHandler(15, 15, 0, TimeUnit.SECONDS));
                        p.addLast(new MessageEncoder());
                        p.addLast(new MessageDecoder());
                        p.addLast(new ClientHandler());
                    }
                });
        this.serviceDiscovery = ExtensionLoader.getExtensionLoader(ServiceDiscovery.class).getExtension("zk");
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.channelProvider = SingletonFactory.getInstance(ChannelProvider.class);
    }

    /**
     * 连接服务端
     *
     * @param inetSocketAddress server address
     * @return the channel
     */
    @SneakyThrows
    public Channel doConnect(InetSocketAddress inetSocketAddress) {
        log.info("连接服务端，其IP地址：{}",inetSocketAddress.toString());
        CompletableFuture<Channel> completableFuture = new CompletableFuture<>();
        bootstrap.connect(inetSocketAddress).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                log.info("与服务端[{}]连接成功 ", inetSocketAddress.toString());
                completableFuture.complete(future.channel());
            } else {
                log.error("连接服务端[{}]失败", inetSocketAddress.toString());
                throw new IllegalStateException();
            }
        });
        return completableFuture.get();
    }

    @Override
    public Object sendRpcRequest(Request rpcRequest) {
        TimeLine timeLine = Monitor.getTimeLine(rpcRequest.getRequestId());


        // 构建服务端将返回的response（在ClientHandler中捕获到服务端返回的Message时，complete此response）
        CompletableFuture<Response<Object>> resultFuture = new CompletableFuture<>();
        // 获取服务端ip
        InetSocketAddress inetSocketAddress = serviceDiscovery.lookUpService(rpcRequest);
        // 获取ip所对应的Channel
        Channel channel = getChannel(inetSocketAddress);
        timeLine.setIpAddress(inetSocketAddress.getHostName());
        timeLine.phaseEndAndNext(TimeLine.Phase.GET_CONNECT);

        if (channel.isActive()) {
            unprocessedRequests.put(rpcRequest.getRequestId(), resultFuture);
            Message rpcMessage = Message.builder().data(rpcRequest)
                    .codec(SerializationTypeEnum.HESSIAN.getCode())
                    .compress(CompressTypeEnum.GZIP.getCode())
                    .messageType(RpcConstants.REQUEST_TYPE).build();
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    log.info("客户端发送请求： [{}]", rpcMessage);
                } else {
                    future.channel().close();
                    resultFuture.completeExceptionally(future.cause());
                    log.error("发送失败:", future.cause());
                }
            });
        } else {
            log.error("连接失效");
        }

        return resultFuture;
    }

    public Channel getChannel(InetSocketAddress inetSocketAddress) {
        Channel channel = channelProvider.get(inetSocketAddress);
        if (channel == null) {
            channel = doConnect(inetSocketAddress);
            channelProvider.set(inetSocketAddress, channel);
        }
        return channel;
    }

}
