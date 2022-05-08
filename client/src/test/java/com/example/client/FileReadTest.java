package com.example.client;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileReadTest {
    @SneakyThrows
    @Test
    public void test(){
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        String fileName = path.getAbsolutePath() + "/static/BigFile-2kb.txt";
        String content = new String(Files.readAllBytes(Paths.get(fileName)));
        System.out.println(content);

    }
}
