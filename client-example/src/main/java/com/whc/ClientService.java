package com.whc;

import com.whc.annotation.RpcReference;
import com.whc.dto.MessageDTO;
import com.whc.service.HelloWorldService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ClientService {

    @RpcReference(version = "version1", group = "test1")
    private HelloWorldService helloWorldService;

    public Integer add(Integer a, Integer b) {
        MessageDTO messageDTO = new MessageDTO(a, b);
        Integer result = helloWorldService.add(messageDTO);
        log.info("result is [{}]", result);
        return result;
    }

}
