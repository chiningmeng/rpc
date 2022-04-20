package com.whc;

import com.whc.annotation.RpcScan;
import com.whc.config.RpcServiceConfig;
import com.whc.remoting.transport.server.NettyServer;
import com.whc.serviceImpl.HelloWorldServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@RpcScan(basePackage = {"com.whc"})
public class ServiceMain {
    public static void main(String[] args) {
        // 通过Spring Bean初始化 服务注册
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ServiceMain.class);
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");

        // 手动服务注册
//        HelloWorldService helloService2 = new HelloWorldServiceImpl();
//        RpcServiceConfig rpcServiceConfig = RpcServiceConfig.builder()
//                .group("test1").version("version1").service(helloService2).build();
//        nettyServer.registerService(rpcServiceConfig);

        nettyServer.start();
    }
}



