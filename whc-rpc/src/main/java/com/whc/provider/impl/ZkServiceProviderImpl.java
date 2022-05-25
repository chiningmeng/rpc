package com.whc.provider.impl;

import com.whc.config.RpcServiceConfig;
import com.whc.enums.RpcErrorMessageEnum;
import com.whc.exception.RpcException;
import com.whc.extensions.ExtensionLoader;
import com.whc.provider.ServiceProvider;
import com.whc.registry.ServiceRegistry;
import com.whc.remoting.transport.server.NettyServer;
import com.whc.utils.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.whc.utils.IpAddressUtils.getIpAddress;


@Slf4j
public class ZkServiceProviderImpl implements ServiceProvider {

    /**
     * key: rpc服务名
     * value: service object
     */
    private final Map<String, Object> serviceMap;
    private final Set<String> registeredService;
    private final ServiceRegistry serviceRegistry;

    public ZkServiceProviderImpl() {
        serviceMap = new ConcurrentHashMap<>();
        registeredService = ConcurrentHashMap.newKeySet();
        serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getExtension("zk");
    }

    @Override
    public void addService(RpcServiceConfig rpcServiceConfig) {
        String rpcServiceName = rpcServiceConfig.getRpcServiceName();
        if (registeredService.contains(rpcServiceName)) {
            return;
        }
        registeredService.add(rpcServiceName);
        serviceMap.put(rpcServiceName, rpcServiceConfig.getService());
        log.info("Add service: {} and interfaces:{}", rpcServiceName, rpcServiceConfig.getService().getClass().getInterfaces());
    }

    @Override
    public Object getService(String rpcServiceName) {
        Object service = serviceMap.get(rpcServiceName);
        if (null == service) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND);
        }
        return service;
    }

    @Override
    public void publishService(RpcServiceConfig rpcServiceConfig) {
        String host = IpAddressUtils.getIpAddress();
        this.addService(rpcServiceConfig);
        serviceRegistry.registerService(rpcServiceConfig.getRpcServiceName(), new InetSocketAddress(host, NettyServer.PORT));
    }

}
