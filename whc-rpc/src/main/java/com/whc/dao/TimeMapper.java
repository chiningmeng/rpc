package com.whc.dao;

import com.whc.dao.entity.Time;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimeMapper {

    public void batchInsertTime(List<Time> list);
}
