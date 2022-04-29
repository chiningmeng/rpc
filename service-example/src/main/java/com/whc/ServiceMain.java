package com.whc;

import com.whc.annotation.RpcScan;
import com.whc.remoting.transport.server.NettyServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan
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

//        NettyServer nettyServer = new NettyServer();
//        nettyServer.registerService(rpcServiceConfig);
        nettyServer.start();
    }
}



