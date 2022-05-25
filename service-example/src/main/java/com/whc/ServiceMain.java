package com.whc;

import com.whc.annotation.RpcScan;
import com.whc.remoting.transport.server.NettyServer;
import com.whc.utils.PropertiesFileUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan
public class ServiceMain {
    public static void main(String[] args) {
//        // 通过Spring Bean初始化 服务注册
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ServiceMain.class);
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
        nettyServer.start();

    }
}



