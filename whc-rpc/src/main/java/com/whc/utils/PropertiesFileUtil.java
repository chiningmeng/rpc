package com.whc.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@Slf4j
public final class PropertiesFileUtil {
    private PropertiesFileUtil() {
    }

    public static Properties readPropertiesFile(String fileName) {
        URL resource = PropertiesFileUtil.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            log.error("无法读文件 [{}]", fileName);
        }
        String path = resource.toString();
        System.out.println(path);
        InputStream is = null;
        try {
            is = resource.openStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Properties properties = null;
        try (InputStreamReader inputStreamReader = new InputStreamReader(is)) {
            properties = new Properties();
            properties.load(inputStreamReader);
        } catch (IOException e) {
            log.error("无法读文件 [{}]", fileName);
        }
        return properties;
    }
}
