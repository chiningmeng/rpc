package com.whc.invoke;

import com.whc.exception.RpcException;
import com.whc.factory.SingletonFactory;
import com.whc.provider.ServiceProvider;
import com.whc.provider.impl.ZkServiceProviderImpl;
import com.whc.remoting.dto.Request;
import com.whc.remoting.dto.Response;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.whc.enums.ResponseCodeEnum.FAIL;

/**
 * 通过反射调用服务中的方法处理请求
 */
@Slf4j
public class ServerInvoker {
    private final ServiceProvider serviceProvider;

    public ServerInvoker() {
        serviceProvider = SingletonFactory.getInstance(ZkServiceProviderImpl.class);
    }

    /**
     * 处理客户端请求
     * @param rpcRequest
     * @return
     */
    public Response<Object> handle(Request rpcRequest) {
        Object service = serviceProvider.getService(rpcRequest.getRpcServiceName());
        return invokeTargetMethod(rpcRequest, service);
    }


    /**
     * @param rpcRequest
     * @param service
     * @return 服务端返回方法处理结果
     */
    private Response<Object> invokeTargetMethod(Request rpcRequest, Object service) {
        Object result;
        Response<Object> rpcResponse;
        try {
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            result = method.invoke(service, rpcRequest.getParameters());
            log.info("服务:[{}] 成功调用方法 :[{}]", rpcRequest.getServiceClassName(), rpcRequest.getMethodName());
        } catch (Exception e) {
            rpcResponse = Response.fail(FAIL,e.getMessage());
            log.error(e.toString());
            return rpcResponse;
        }
        return Response.success(result,rpcRequest.getRequestId());
    }
}
