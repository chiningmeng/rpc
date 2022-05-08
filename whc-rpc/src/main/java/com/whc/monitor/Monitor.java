package com.whc.monitor;

import com.whc.dao.TimeMapper;
import com.whc.dao.entity.Time;
import com.whc.dao.mapstruct.TimeLineConverter;
import com.whc.monitor.time.ClientTimeLine;
import com.whc.monitor.time.ServerTimeLine;
import com.whc.monitor.time.TimeLine;
import com.whc.utils.ThreadPoolFactoryUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
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
    private static final int TIME_LINE_LIST_SIZE = 500;
    private static final Map<String, TimeLine> TIME_LINE_MAP = new ConcurrentHashMap<>();
    private static final List<Time> finishedTimeLineList = new ArrayList<>(TIME_LINE_LIST_SIZE);
    private static final ExecutorService executorService = ThreadPoolFactoryUtil.createCustomThreadPoolIfAbsent("monitor-thread_pool");
    private static final Queue<SqlSession> SessionQueue = new PriorityQueue<>();

    public static void addTimeLine(String requestId, TimeLine timeLine) {
        TIME_LINE_MAP.put(requestId, timeLine);
    }

    public static TimeLine getTimeLine(String requestId) {
        return TIME_LINE_MAP.get(requestId);
    }

    public static void addFinishedTimeLine(TimeLine timeLine) {
        synchronized (finishedTimeLineList) {
            if (finishedTimeLineList.size() <= TIME_LINE_LIST_SIZE-1) {
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
                        SqlSession sqlSession =SessionQueue.peek();
                        TimeMapper timeMapper;
                        if (sqlSession == null) {
                            sqlSession = getTimeMapperIfAbsent();
                        }
                        timeMapper = sqlSession.getMapper(TimeMapper.class);
                        timeMapper.batchInsertTime(finishedTimeLineList);
                        sqlSession.commit();
                        finishedTimeLineList.clear();
                    }
                });
            }
        }
    }

    private static SqlSession getTimeMapperIfAbsent() {
        synchronized (SessionQueue) {
            if (SessionQueue.peek() == null) {
                String resource = "META-INF/mybatis-config.xml";
                InputStream inputStream = null;
                try {
                    inputStream = Resources.getResourceAsStream(resource);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                SessionQueue.add(sqlSessionFactory.openSession());
            }
        }
        return SessionQueue.peek();
    }

}
