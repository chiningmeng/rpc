package com.whc;

import com.whc.annotation.RpcReference;
import com.whc.annotation.RpcScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Slf4j
@RpcScan
public class ClientMain {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ClientMain.class);
        ClientService clientService = (ClientService) applicationContext.getBean("clientService");
        log.info("计算结果为：{}", clientService.add(1, 2));
    }
}
