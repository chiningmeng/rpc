package com.whc.remoting.transport;


import com.whc.extensions.SPI;
import com.whc.remoting.dto.Request;


@SPI
public interface RequestTransport {
    /**
     * 发送请求，并返回结结果
     *
     * @param rpcRequest
     * @return
     */
    Object sendRpcRequest(Request rpcRequest);
}
