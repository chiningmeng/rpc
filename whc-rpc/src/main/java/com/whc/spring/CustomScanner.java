package com.whc.spring;


import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * A bean definition scanner that detects bean candidates on the classpath, registering
 * corresponding bean definitions with a given registry (BeanFactory or ApplicationContext).
 * Candidate classes are detected through configurable type filters.
 *
 * The default filters include classes that are annotated with
 * Spring's @Component, @Repository, @Service, or @Controller stereotype.
 */
public class CustomScanner extends ClassPathBeanDefinitionScanner {

    public CustomScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annotationType) {
        super(registry);
        //向filter中添加所需 register 的 BeanDefinition 上使用的注解类型
        super.addIncludeFilter(new AnnotationTypeFilter(annotationType));
    }

    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}