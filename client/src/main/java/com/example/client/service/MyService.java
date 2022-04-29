package com.example.client.service;

import com.whc.HelloWorldService;
import com.whc.MessageDTO;
import com.whc.annotation.RpcReference;
import com.whc.annotation.RpcScan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RpcScan
public class MyService {
    @RpcReference(version = "version1", group = "test1")
    private HelloWorldService helloWorldService;

    public Integer add(Integer a, Integer b) {
        MessageDTO messageDTO = new MessageDTO(a, b);
        Integer result = helloWorldService.add(messageDTO);
        log.info("result is [{}]", result);
        return result;
    }
}
