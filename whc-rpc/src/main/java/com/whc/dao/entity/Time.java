package com.whc.dao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Time implements Serializable {
    private Long uid;

    private String traceId;

    private String whereHappen;

    private Boolean isSuccess;

    private Long startTimestamp;

    private Long endTimestamp;

    private Integer serialize;

    private Integer deserialize;

    private Long total;

    private Integer getConnect;

    private Integer loadBalance;

    private Integer waitForResponse;

    private Integer getServerList;

    private Integer handle;

    private Long requestSize;

    private Long responseSize;
}