package com.whc.rpc.remoting.transport.netty.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class NettyClientTest {
    @Resource
    private NettyClient nettyClient;


    @Test
    public void test(){
        nettyClient.start();
        try {
            Thread.sleep(999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}