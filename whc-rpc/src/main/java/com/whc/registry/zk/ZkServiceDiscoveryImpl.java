package com.whc.registry.zk;


import com.whc.enums.RpcErrorMessageEnum;
import com.whc.exception.RpcException;
import com.whc.extensions.ExtensionLoader;
import com.whc.loadbalance.LoadBalance;
import com.whc.monitor.Monitor;
import com.whc.monitor.time.TimeLine;
import com.whc.registry.ServiceDiscovery;
import com.whc.registry.zk.util.CuratorUtils;
import com.whc.remoting.dto.Request;
import com.whc.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 服务发现
 */
@Slf4j
public class ZkServiceDiscoveryImpl implements ServiceDiscovery {
    private final LoadBalance loadBalance;

    public ZkServiceDiscoveryImpl() {
        this.loadBalance = ExtensionLoader.getExtensionLoader(LoadBalance.class).getExtension("loadBalance");
    }

    @Override
    public InetSocketAddress lookUpService(Request rpcRequest) {
        TimeLine timeLine = Monitor.getTimeLine(rpcRequest.getRequestId());
        String rpcServiceName = rpcRequest.getRpcServiceName();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, rpcServiceName);
        timeLine.phaseEndAndNext(TimeLine.Phase.GET_SERVER_LIST);
        if (CollectionUtil.isEmpty(serviceUrlList)) {
            throw new RpcException(RpcErrorMessageEnum.SERVICE_CAN_NOT_BE_FOUND, rpcServiceName);
        }
        // load balancing
        String targetServiceUrl = loadBalance.selectServiceAddress(serviceUrlList, rpcRequest);
        timeLine.phaseEndAndNext(TimeLine.Phase.LOAD_BALANCE);
        log.info("Successfully found the service address:[{}]", targetServiceUrl);

        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);
        return new InetSocketAddress(host, port);
    }
}
