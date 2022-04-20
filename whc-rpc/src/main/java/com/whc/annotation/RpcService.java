package com.whc.annotation;


import java.lang.annotation.*;

/**
 * 标记服务接口的实现
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface RpcService {

    /**
     * 服务版本
     */
    String version() default "";

    String group() default "";

}
