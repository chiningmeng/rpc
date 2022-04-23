package com.whc.annotation;

import com.whc.remoting.transport.server.NettyServer;
import com.whc.spring.CustomScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import({CustomScannerRegistrar.class})
public @interface RpcScan {

}
