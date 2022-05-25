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
        return new BigFileDTO(bigFileDTO.getBigFile());
    }
}
