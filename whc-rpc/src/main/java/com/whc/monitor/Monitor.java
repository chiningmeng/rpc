package com.whc.monitor;

import com.whc.dao.TimeMapper;
import com.whc.dao.entity.Time;
import com.whc.dao.mapstruct.TimeLineConverter;
import com.whc.monitor.time.ClientTimeLine;
import com.whc.monitor.time.ServerTimeLine;
import com.whc.monitor.time.TimeLine;
import com.whc.utils.ThreadPoolFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Component
public class Monitor {
    private static final int TIME_LINE_LIST_SIZE = 500;

    private static final Map<String, TimeLine> TIME_LINE_MAP = new ConcurrentHashMap<>();

    private static final List<Time> finishedTimeLineList = new ArrayList<>(TIME_LINE_LIST_SIZE);

    private static final ExecutorService executorService = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("monitor-thread_pool");

    @Autowired
    private TimeMapper timeMap;

    private static TimeMapper timeMapper;

    @PostConstruct
    public void beforeInit() {
        timeMapper = timeMap;
    }

    public static void addTimeLine(String requestId, TimeLine timeLine) {
        TIME_LINE_MAP.put(requestId, timeLine);
    }

    public static TimeLine getTimeLine(String requestId) {
        return TIME_LINE_MAP.get(requestId);
    }

    public static void addFinishedTimeLine(TimeLine timeLine) {
        synchronized (finishedTimeLineList) {
            if (finishedTimeLineList.size() < TIME_LINE_LIST_SIZE-1) {
                if (timeLine instanceof ClientTimeLine) {
                    Time time = TimeLineConverter.INSTANCE.clientToTimePO((ClientTimeLine) timeLine);
                    finishedTimeLineList.add(time);
                } else if (timeLine instanceof ServerTimeLine) {
                    Time time = TimeLineConverter.INSTANCE.ServerToTimePO((ServerTimeLine) timeLine);
                    finishedTimeLineList.add(time);
                }
            } else {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }

}
