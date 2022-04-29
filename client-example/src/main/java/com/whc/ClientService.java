package com.whc;

import com.whc.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
