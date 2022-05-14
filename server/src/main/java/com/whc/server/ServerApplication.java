package com.whc.server;

import com.whc.annotation.RpcScan;
import com.whc.remoting.transport.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RpcScan
@SpringBootApplication
public class ServerApplication {


    public static void main(String[] args) {

        SpringApplication.run(ServerApplication.class, args);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ServerApplication.class);
        NettyServer nettyServer = (NettyServer) applicationContext.getBean("nettyServer");
        nettyServer.start();
    }

}
