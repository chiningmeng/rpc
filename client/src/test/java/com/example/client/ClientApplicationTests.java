package com.example.client;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
class ClientApplicationTests {

    @Test
    void generateBigFile() {
        try {
            String file = RandomStringUtils.randomAscii(2*1024*1024);
            BufferedWriter out = new BufferedWriter(new FileWriter("BigFile-2mb.txt"));
            out.write(file);
            out.close();
        } catch (IOException e) {
        }
    }

}
