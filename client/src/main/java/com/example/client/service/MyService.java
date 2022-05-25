package com.example.client.service;

import com.whc.dto.BigFileDTO;
import com.whc.service.BigFileTransportService;
import com.whc.service.HelloWorldService;
import com.whc.dto.MessageDTO;
import com.whc.annotation.RpcReference;
import com.whc.annotation.RpcScan;
import com.whc.utils.PropertiesFileUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
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
        return result;
    }

    @SneakyThrows
    public Boolean transport() {
        String fileName = "BigFile-2kb.txt";
        URL resource = PropertiesFileUtil.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            log.error("无法读文件 [{}]", fileName);
        }
        String path = resource.toString();
        System.out.println(path);
        InputStream is = resource.openStream();
        String content = IOUtils.toString(is, StandardCharsets.UTF_8);
        String returnContent = bigFileTransportService.transport(new BigFileDTO(content)).getBigFile();
        if (content.equals(returnContent)) {
            return true;
        }
        log.error("传输有误");
        return false;
    }

}
