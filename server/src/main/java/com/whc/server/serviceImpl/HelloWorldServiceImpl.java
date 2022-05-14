package com.whc.server.serviceImpl;

import com.whc.annotation.RpcService;
import com.whc.dto.MessageDTO;
import com.whc.service.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
@RpcService(group = "test1", version = "version1")
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public Integer add(MessageDTO messageDTO) {
        log.info("get arg: [{}]", messageDTO.toString());
        return messageDTO.getA() + messageDTO.getB();
    }
}
