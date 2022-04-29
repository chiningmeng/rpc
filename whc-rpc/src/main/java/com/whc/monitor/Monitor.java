package com.whc.monitor;

import com.whc.monitor.time.TimeLine;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Monitor {
    private static final Map<String, TimeLine> TIME_LINE_MAP = new ConcurrentHashMap<>();

    public static void addTimeLine(String requestId, TimeLine timeLine) {
        TIME_LINE_MAP.put(requestId, timeLine);
    }

    public static TimeLine getTimeLine(String requestId) {
        return TIME_LINE_MAP.get(requestId);
    }

}
