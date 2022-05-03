package com.whc.dao;

import com.whc.dao.entity.Time;
import com.whc.monitor.time.TimeLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimeMapper {

    void batchInsertTime(List<Time> list);
}
