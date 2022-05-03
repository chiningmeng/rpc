package com.whc.monitor;

import com.whc.dao.TimeMapper;
import com.whc.dao.entity.Time;
import com.whc.dao.mapstruct.TimeLineConverter;
import com.whc.monitor.time.ClientTimeLine;
import com.whc.monitor.time.ServerTimeLine;
import com.whc.monitor.time.TimeLine;
import com.whc.utils.ThreadPoolFactoryUtil;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

@Component
public class Monitor {
    private static final int TIME_LINE_LIST_SIZE = 3;
    private static final Map<String, TimeLine> TIME_LINE_MAP = new ConcurrentHashMap<>();
    private static final List<Time> finishedTimeLineList = new ArrayList<>(TIME_LINE_LIST_SIZE);
    private static final ExecutorService executorService = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("monitor-thread_pool");
    private static final Queue<TimeMapper> MAPPER_LIST = new PriorityQueue<>();

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
                        TimeMapper timeMapper = MAPPER_LIST.peek();
                        if (timeMapper == null) {
                            timeMapper = getTimeMapperIfAbsent();
                        }
                        timeMapper.batchInsertTime(finishedTimeLineList);
                        finishedTimeLineList.clear();
                    }
                });
            }
        }
    }

    private static TimeMapper getTimeMapperIfAbsent() {
        synchronized (MAPPER_LIST) {
            String resource = "META-INF/mybatis-config.xml";
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            TimeMapper timeMapper = sqlSessionFactory.openSession().getMapper(TimeMapper.class);
            MAPPER_LIST.add(timeMapper);
        }
        return MAPPER_LIST.peek();
    }

}
