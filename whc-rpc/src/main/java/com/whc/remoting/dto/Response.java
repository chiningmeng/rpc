package com.whc.remoting.dto;

import com.whc.enums.ResponseCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    private static final long serialVersionUID = 715745410605631233L;
    private String requestId;

    /**
     *  code
     */
    private Integer code;

    /**
     *  message
     */
    private String message;

    /**
     * 消息体
     */
    private T data;

    public static <T> Response<T> success(T data, String requestId) {
        Response<T> response = new Response<>();
        response.setCode(ResponseCodeEnum.SUCCESS.getCode());
        response.setMessage(ResponseCodeEnum.SUCCESS.getMessage());
        response.setRequestId(requestId);
        if (null != data) {
            response.setData(data);
        }
        return response;
    }

    public static <T> Response<T> fail(ResponseCodeEnum rpcResponseCodeEnum) {
        Response<T> response = new Response<>();
        response.setCode(rpcResponseCodeEnum.getCode());
        response.setMessage(rpcResponseCodeEnum.getMessage());
        return response;
    }
}
