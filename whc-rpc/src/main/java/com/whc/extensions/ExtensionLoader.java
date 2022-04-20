package com.whc.extensions;

import com.whc.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * dubbo spi: https://dubbo.apache.org/zh/docs/v2.7/dev/source/dubbo-spi/
 */
@Slf4j
public final class ExtensionLoader<T> {

    private static final String SERVICE_DIRECTORY = "META-INF/extensions/";
    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADER_MAP = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Object> EXTENSION_INSTANCE_MAP = new ConcurrentHashMap<>();

    private final Class<?> type;
    private final Map<String, Holder<Object>> cachedInstanceMap = new ConcurrentHashMap<>();
    private final Holder<Map<String, Class<?>>> cachedClassHolder = new Holder<>();

    private ExtensionLoader(Class<?> type) {
        this.type = type;
    }

    /**
     * 获取ExtensionLoader ： 先从缓存中取已加载好的，若无则加载并放入缓存中
     * @param type
     * @param <S>
     * @return
     */
    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type should not be null.");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type must be an interface.");
        }
        if (type.getAnnotation(SPI.class) == null) {
            throw new IllegalArgumentException("Extension type must be annotated by @SPI");
        }
        // 先从内存中取，若无则新建一个
        ExtensionLoader<S> extensionLoader = (ExtensionLoader<S>) EXTENSION_LOADER_MAP.get(type);
        if (Objects.isNull(extensionLoader)) {
            EXTENSION_LOADER_MAP.putIfAbsent(type, new ExtensionLoader<S>(type));
            extensionLoader = (ExtensionLoader<S>) EXTENSION_LOADER_MAP.get(type);
        }
        return extensionLoader;
    }

    /**
     * 返回指定名称所对应的实现
     * @param name
     * @return
     */
    public T getExtension(String name) {
        if (StringUtil.isBlank(name)) {
            throw new IllegalArgumentException("Extension name should not be null or empty.");
        }
        // 先去缓存中找，若没有则创建一个
        Holder<Object> holder = cachedInstanceMap.get(name);
        if (holder == null) {
            cachedInstanceMap.putIfAbsent(name, new Holder<>());
            holder = cachedInstanceMap.get(name);
        }
        // 单例
        Object instance = holder.get();
        if (Objects.isNull(instance)) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    /**
     * 加载所有T类扩展，返回指定名称所对应的实现
     * @param name
     * @return
     */
    private T createExtension(String name) {
        Class<?> clazz = getExtensionClassMap().get(name);
        if (Objects.isNull(clazz)) {
            throw new RuntimeException("No such extension of the name " + name);
        }
        T instance = (T) EXTENSION_INSTANCE_MAP.get(clazz);
        if (Objects.isNull(instance)) {
            try {
                EXTENSION_INSTANCE_MAP.putIfAbsent(clazz, clazz.newInstance());
                instance = (T) EXTENSION_INSTANCE_MAP.get(clazz);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return instance;
    }

    /**
     * 获取所有扩展类
     * @return
     */
    private Map<String, Class<?>> getExtensionClassMap() {
        // 从内存中取加载过的扩展类
        Map<String, Class<?>> stringClassMap = cachedClassHolder.get();
        // 非空校验
        if (Objects.isNull(stringClassMap)) {
            synchronized (cachedClassHolder) {
                stringClassMap = cachedClassHolder.get();
                if (Objects.isNull(stringClassMap)) {
                    stringClassMap = new HashMap<>();
                    // 从扩展目录中获取所有扩展
                    loadDirectory(stringClassMap);
                    cachedClassHolder.set(stringClassMap);
                }
            }
        }
        return stringClassMap;
    }

    /**
     *
     * @param extensionClassMap
     */
    private void loadDirectory(Map<String, Class<?>> extensionClassMap) {
        String fileName = SERVICE_DIRECTORY + type.getName();
        try {
            Enumeration<URL> urls;
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            urls = classLoader.getResources(fileName);
            if (Objects.nonNull(urls)) {
                while (urls.hasMoreElements()) {
                    URL resourceUrl = urls.nextElement();
                    loadResource(extensionClassMap, classLoader, resourceUrl);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void loadResource(Map<String, Class<?>> extensionClassMap, ClassLoader classLoader, URL resourceUrl) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceUrl.openStream(), UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                final int indexOfComment = line.indexOf('#');
                if (indexOfComment >= 0) {
                    // "#" 为注释
                    line = line.substring(0, indexOfComment);
                }
                line = line.trim();
                if (line.length() > 0) {
                    try {
                        final int indexOfEquality = line.indexOf('=');
                        String name = line.substring(0, indexOfEquality).trim();
                        String className = line.substring(indexOfEquality + 1).trim();

                        if (StringUtil.notBlank(name) && StringUtil.notBlank(className)) {
                            Class<?> extensionClass = classLoader.loadClass(className);
                            extensionClassMap.put(name, extensionClass);
                        }
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                    }
                }

            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
