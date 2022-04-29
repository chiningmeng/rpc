package com.whc.loadbalance;

import com.whc.extensions.SPI;
import com.whc.remoting.dto.Request;

import java.util.List;

/**
 * 负载均衡
 */
@SPI
public interface LoadBalance {

    /**
     * 返回serviceUrlList里的一个服务地址
     *
     * @param serviceUrlList
     * @param rpcRequest
     * @return
     */
    String selectServiceAddress(List<String> serviceUrlList, Request rpcRequest);
}
