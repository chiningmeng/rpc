package com.whc.monitor.time;

import com.whc.dao.TimeMapper;
import com.whc.enums.WhereEnum;
import com.whc.monitor.Monitor;
import lombok.Data;

@Data
public abstract class TimeLine {
    protected String traceId;
    protected WhereEnum where;
    protected Boolean isSuccess;

    /**
     * 调用开始时间
     */
    protected long startTimeStamp;

    /**
     * 调用结束时间
     */
    protected long endTimeStamp = -1;

    /**
     * 阶段开始时间
     */
    protected long phaseStarTimeStamp;

    /**
     * 序列化时间
     */
    protected int serializeTime;

    /**
     * 反序列化时间
     */
    protected int deserializeTime;

    /**
     * 总耗时
     */
    protected long totalTime;

    public TimeLine() {
    }

    public TimeLine(String uid, WhereEnum where) {
        this.traceId = uid;
        this.where = where;
        this.isSuccess = false;
        startTimeStamp = System.currentTimeMillis();
        phaseStarTimeStamp = startTimeStamp;
    }

    public void phaseStart() {
        phaseStarTimeStamp = System.currentTimeMillis();
    }

    public abstract void phaseEnd(Phase phase);

    public void phaseEndAndNext(Phase phase) {
        phaseEnd(phase);
        phaseStart();
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
        phaseStarTimeStamp = startTimeStamp;
    }

    public void phaseStartWithTimeStamp(long startTimeStamp) {
        phaseStarTimeStamp = startTimeStamp;
    }

    public void setTotalTime() {
        if (endTimeStamp == -1) {
            endTimeStamp = System.currentTimeMillis();
        }
        this.isSuccess = true;
        totalTime = endTimeStamp - startTimeStamp;
        Monitor.addFinishedTimeLine(this);
    }

    public void phaseEndWithTimeStamp(Phase phase, long phaseStarTimeStamp) {
        this.phaseStarTimeStamp = phaseStarTimeStamp;
        phaseEnd(phase);
    }

    public enum Phase {
        // both
        /**
         * 序列化
         */
        SERIALIZE,

        /**
         * 反序列化
         */
        DESERIALIZE,

        // client
        /**
         * 获取服务
         */
        GET_SERVER_LIST,

        /**
         * 负载均衡
         */
        LOAD_BALANCE,
        /**
         * 取得连接
         */
        GET_CONNECT,

        /**
         * 等待结果
         */
        WAIT_FOR_RESPONSE,

        // server
        /**
         * 服务处理
         */
        HANDLE
    }
}
