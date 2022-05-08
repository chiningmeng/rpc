package com.example.client.service;

import com.whc.dto.BigFileDTO;
import com.whc.service.BigFileTransportService;
import com.whc.service.HelloWorldService;
import com.whc.dto.MessageDTO;
import com.whc.annotation.RpcReference;
import com.whc.annotation.RpcScan;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@Slf4j
@RpcScan
public class MyService {
    @RpcReference(version = "version1", group = "test1")
    private HelloWorldService helloWorldService;
    @RpcReference(version = "1", group = "1")
    private BigFileTransportService bigFileTransportService;

    public Integer add(Integer a, Integer b) {
        MessageDTO messageDTO = new MessageDTO(a, b);
        Integer result = helloWorldService.add(messageDTO);
        log.info("result is [{}]", result);
        return result;
    }

    @SneakyThrows
    public Boolean transport() {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        String fileName = path.getAbsolutePath() + "/static/BigFile-2kb.txt";
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        log.info("content: [{}]",content);
        String returnContent = bigFileTransportService.transport(new BigFileDTO(content)).getBigFile();
        if (content.equals(returnContent)) {
            return true;
        }
        return false;
    }
}
