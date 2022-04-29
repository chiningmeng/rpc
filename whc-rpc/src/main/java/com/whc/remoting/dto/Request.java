package com.whc.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {
    private static final long serialVersionUID = 1905122041950251207L;
    private String requestId;
    private String serviceClassName;
    private String methodName;
    private Object[] parameters;
    private Class<?>[] paramTypes;

    public String getRpcServiceName() {
        return this.getServiceClassName();
    }
}
