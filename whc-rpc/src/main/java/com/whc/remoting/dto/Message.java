package com.whc.remoting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    /**
     * 消息类型：判断是普通消息还是心跳监测消息
     */
    private byte messageType;

    /**
     * 序列化类型
     */
    private byte codec;

    /**
     * 请求id
     */
    private int requestId;

    /**
     * 压缩类型
     */
    private byte compress;

    /**
     * 数据
     */
    private Object data;
}
