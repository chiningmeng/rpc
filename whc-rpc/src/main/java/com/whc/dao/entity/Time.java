package com.whc.dao.entity;

import java.io.Serializable;

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

    private static final long serialVersionUID = 1L;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId == null ? null : traceId.trim();
    }

    public String getWhereHappen() {
        return whereHappen;
    }

    public void setWhereHappen(String whereHappen) {
        this.whereHappen = whereHappen == null ? null : whereHappen.trim();
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Long getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Long startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Long getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Long endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Integer getSerialize() {
        return serialize;
    }

    public void setSerialize(Integer serialize) {
        this.serialize = serialize;
    }

    public Integer getDeserialize() {
        return deserialize;
    }

    public void setDeserialize(Integer deserialize) {
        this.deserialize = deserialize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getGetConnect() {
        return getConnect;
    }

    public void setGetConnect(Integer getConnect) {
        this.getConnect = getConnect;
    }

    public Integer getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(Integer loadBalance) {
        this.loadBalance = loadBalance;
    }

    public Integer getWaitForResponse() {
        return waitForResponse;
    }

    public void setWaitForResponse(Integer waitForResponse) {
        this.waitForResponse = waitForResponse;
    }

    public Integer getGetServerList() {
        return getServerList;
    }

    public void setGetServerList(Integer getServerList) {
        this.getServerList = getServerList;
    }

    public Integer getHandle() {
        return handle;
    }

    public void setHandle(Integer handle) {
        this.handle = handle;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Time other = (Time) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
            && (this.getTraceId() == null ? other.getTraceId() == null : this.getTraceId().equals(other.getTraceId()))
            && (this.getWhereHappen() == null ? other.getWhereHappen() == null : this.getWhereHappen().equals(other.getWhereHappen()))
            && (this.getIsSuccess() == null ? other.getIsSuccess() == null : this.getIsSuccess().equals(other.getIsSuccess()))
            && (this.getStartTimestamp() == null ? other.getStartTimestamp() == null : this.getStartTimestamp().equals(other.getStartTimestamp()))
            && (this.getEndTimestamp() == null ? other.getEndTimestamp() == null : this.getEndTimestamp().equals(other.getEndTimestamp()))
            && (this.getSerialize() == null ? other.getSerialize() == null : this.getSerialize().equals(other.getSerialize()))
            && (this.getDeserialize() == null ? other.getDeserialize() == null : this.getDeserialize().equals(other.getDeserialize()))
            && (this.getTotal() == null ? other.getTotal() == null : this.getTotal().equals(other.getTotal()))
            && (this.getGetConnect() == null ? other.getGetConnect() == null : this.getGetConnect().equals(other.getGetConnect()))
            && (this.getLoadBalance() == null ? other.getLoadBalance() == null : this.getLoadBalance().equals(other.getLoadBalance()))
            && (this.getWaitForResponse() == null ? other.getWaitForResponse() == null : this.getWaitForResponse().equals(other.getWaitForResponse()))
            && (this.getGetServerList() == null ? other.getGetServerList() == null : this.getGetServerList().equals(other.getGetServerList()))
            && (this.getHandle() == null ? other.getHandle() == null : this.getHandle().equals(other.getHandle()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getTraceId() == null) ? 0 : getTraceId().hashCode());
        result = prime * result + ((getWhereHappen() == null) ? 0 : getWhereHappen().hashCode());
        result = prime * result + ((getIsSuccess() == null) ? 0 : getIsSuccess().hashCode());
        result = prime * result + ((getStartTimestamp() == null) ? 0 : getStartTimestamp().hashCode());
        result = prime * result + ((getEndTimestamp() == null) ? 0 : getEndTimestamp().hashCode());
        result = prime * result + ((getSerialize() == null) ? 0 : getSerialize().hashCode());
        result = prime * result + ((getDeserialize() == null) ? 0 : getDeserialize().hashCode());
        result = prime * result + ((getTotal() == null) ? 0 : getTotal().hashCode());
        result = prime * result + ((getGetConnect() == null) ? 0 : getGetConnect().hashCode());
        result = prime * result + ((getLoadBalance() == null) ? 0 : getLoadBalance().hashCode());
        result = prime * result + ((getWaitForResponse() == null) ? 0 : getWaitForResponse().hashCode());
        result = prime * result + ((getGetServerList() == null) ? 0 : getGetServerList().hashCode());
        result = prime * result + ((getHandle() == null) ? 0 : getHandle().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", traceId=").append(traceId);
        sb.append(", whereHappen=").append(whereHappen);
        sb.append(", isSuccess=").append(isSuccess);
        sb.append(", startTimestamp=").append(startTimestamp);
        sb.append(", endTimestamp=").append(endTimestamp);
        sb.append(", serialize=").append(serialize);
        sb.append(", deserialize=").append(deserialize);
        sb.append(", total=").append(total);
        sb.append(", getConnect=").append(getConnect);
        sb.append(", loadBalance=").append(loadBalance);
        sb.append(", waitForResponse=").append(waitForResponse);
        sb.append(", getServerList=").append(getServerList);
        sb.append(", handle=").append(handle);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}