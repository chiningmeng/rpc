package com.whc.proxy;

import com.whc.config.RpcServiceConfig;
import com.whc.enums.WhereEnum;
import com.whc.enums.ResponseCodeEnum;
import com.whc.enums.RpcErrorMessageEnum;
import com.whc.exception.RpcException;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.ClientTimeLine;
import com.whc.monitor.time.TimeLine;
import com.whc.remoting.constants.RpcConstants;
import com.whc.remoting.dto.Request;
import com.whc.remoting.dto.Response;
import com.whc.remoting.transport.RequestTransport;
import com.whc.remoting.transport.client.NettyClient;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 动态代理类
 * 如果调用一个method，实际调用的是invoke
 * 隐藏客户端与服务端间通信的细节
 */
@Slf4j
public class RpcClientProxy implements InvocationHandler {

    private static final String INTERFACE_NAME = "interfaceName";

    private final RequestTransport requestTransport;
    private final RpcServiceConfig serviceConfig;

    public RpcClientProxy(RequestTransport requestTransport, RpcServiceConfig serviceConfig) {
        this.requestTransport = requestTransport;
        this.serviceConfig = serviceConfig;
    }


    public RpcClientProxy(RequestTransport requestTransport) {
        this.requestTransport = requestTransport;
        this.serviceConfig = new RpcServiceConfig();
    }

    /**
     * get the proxy object
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    /**
     * 动态代理类调用其方法时调用
     * The proxy object is the object you get through the getProxy method.
     */
    @SneakyThrows
    @SuppressWarnings("unchecked")
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.info("invoked by method: [{}]", method.getName());
        Request rpcRequest = Request.builder().methodName(method.getName())
                .parameters(args)
                .serviceClassName(method.getDeclaringClass().getName())
                .paramTypes(method.getParameterTypes())
                .requestId(UUID.randomUUID().toString())
                .group(serviceConfig.getGroup())
                .version(serviceConfig.getVersion())
                .build();
        Response<Object> response = null;

        TimeLine timeLine = new ClientTimeLine(rpcRequest.getRequestId(), WhereEnum.Client);
        Monitor.addTimeLine(rpcRequest.getRequestId(), timeLine);

        if (requestTransport instanceof NettyClient) {
            //todo CompletableFuture
            long phaseStarTimeStamp = System.currentTimeMillis();
            CompletableFuture<Response<Object>> completableFuture = (CompletableFuture<Response<Object>>) requestTransport.sendRpcRequest(rpcRequest);
            response = completableFuture.get();

            timeLine.phaseEndWithTimeStamp(TimeLine.Phase.WAIT_FOR_RESPONSE, phaseStarTimeStamp);
            timeLine.setTotalTime();
        }
        this.check(response, rpcRequest);
        return response.getData();
    }

    private void check(Response<Object> rpcResponse, Request rpcRequest) {
        if (rpcResponse == null) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getServiceClassName());
        }

        if (!rpcRequest.getRequestId().equals(rpcResponse.getRequestId())) {
            throw new RpcException(RpcErrorMessageEnum.REQUEST_NOT_MATCH_RESPONSE, INTERFACE_NAME + ":" + rpcRequest.getServiceClassName());
        }

        if (rpcResponse.getCode() == null || !rpcResponse.getCode().equals(ResponseCodeEnum.SUCCESS.getCode())) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_INVOCATION_FAILURE, INTERFACE_NAME + ":" + rpcRequest.getServiceClassName());
        }
    }
}
