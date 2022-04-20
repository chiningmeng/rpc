package com.whc.annotation;


import java.lang.annotation.*;

/**
 * 自动注入服务实现
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Inherited
public @interface RpcReference {

    /**
     * 服务版本
     */
    String version() default "";


    String group() default "";

}
