package com.whc.registry;


import com.whc.extensions.SPI;
import com.whc.remoting.dto.Request;

import java.net.InetSocketAddress;


@SPI
public interface ServiceDiscovery {
    /**
     * lookup service by rpcServiceName
     *
     * @param rpcRequest rpc service pojo
     * @return service address
     */
    InetSocketAddress lookUpService(Request rpcRequest);
}
