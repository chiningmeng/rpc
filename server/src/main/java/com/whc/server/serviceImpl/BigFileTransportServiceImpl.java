package com.whc.server.serviceImpl;

import com.whc.annotation.RpcService;
import com.whc.dto.BigFileDTO;
import com.whc.service.BigFileTransportService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
@RpcService(group = "1", version = "1")
public class BigFileTransportServiceImpl implements BigFileTransportService {

    @SneakyThrows
    @Override
    public BigFileDTO transport(BigFileDTO bigFileDTO) {
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        String fileName = path.getAbsolutePath() + "/static/BigFile-2kb.txt";
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        log.info("content: [{}]",content);
        String requestContent = bigFileDTO.getBigFile();
        log.info("requestContent: [{}]",requestContent);
        if (content.equals(requestContent)) {
            return new BigFileDTO(content);
        }
        return new BigFileDTO("");
    }
}
