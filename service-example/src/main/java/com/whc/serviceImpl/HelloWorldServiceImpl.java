package com.whc.serviceImpl;

import com.whc.HelloWorldService;
import com.whc.MessageDTO;
import com.whc.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RpcService(group = "test1", version = "version1")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public Integer add(MessageDTO messageDTO) {
        log.info("get arg: [{}]", messageDTO.toString());
        return messageDTO.getA() + messageDTO.getB();
    }
}
