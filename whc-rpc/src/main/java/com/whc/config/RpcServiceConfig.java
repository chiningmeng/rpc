package com.whc.config;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RpcServiceConfig {
    /**
     * 服务版本
     */
    private String version = "";
    /**
     * 服务分组
     */
    private String group = "";

    /**
     * 服务对象实例
     */
    private Object service;

    public String getRpcServiceName() {
        return this.service.getClass().getInterfaces()[0].getCanonicalName();
    }

}
