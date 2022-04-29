package com.whc.provider;


import com.whc.config.RpcServiceConfig;

/**
 * 存储&提供服务
 */
public interface ServiceProvider {

    /**
     * @param rpcServiceConfig
     */
    void addService(RpcServiceConfig rpcServiceConfig);

    /**
     * @param rpcServiceName
     * @return service object
     */
    Object getService(String rpcServiceName);

    /**
     * @param rpcServiceConfig
     */
    void publishService(RpcServiceConfig rpcServiceConfig);

}
