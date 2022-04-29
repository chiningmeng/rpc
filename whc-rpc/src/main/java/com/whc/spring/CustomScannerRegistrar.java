package com.whc.spring;


import com.whc.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * RpcScan import此类，在标注@RpcScan注解的类下 属性bean自动注入前调用
 */
@Slf4j
public class CustomScannerRegistrar implements ImportBeanDefinitionRegistrar {
    private static final String THIS_PACKAGE = "com.whc";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {

        // 扫描RpcService注解
        CustomScanner rpcServiceScanner = new CustomScanner(beanDefinitionRegistry, RpcService.class);
        rpcServiceScanner.scan(THIS_PACKAGE);
    }

}
