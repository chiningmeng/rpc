package com.whc.loadbalance.loadbalancer;

import com.whc.loadbalance.AbstractLoadBalance;
import com.whc.remoting.dto.Request;

import java.util.List;
import java.util.Random;

/**
 * 随机策略
 */
public class RandomLoadBalance extends AbstractLoadBalance {
    @Override
    protected String doSelect(List<String> serviceAddresses, Request rpcRequest) {
        Random random = new Random();
        return serviceAddresses.get(random.nextInt(serviceAddresses.size()));
    }
}
