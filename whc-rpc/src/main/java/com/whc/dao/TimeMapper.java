package com.whc.dao;

import com.whc.dao.entity.Time;
import com.whc.dao.entity.TimeExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface TimeMapper {
    @SelectProvider(type=TimeSqlProvider.class, method="countByExample")
    long countByExample(TimeExample example);

    @DeleteProvider(type=TimeSqlProvider.class, method="deleteByExample")
    int deleteByExample(TimeExample example);

    @Delete({
        "delete from rpc_time",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long uid);

    @Insert({
        "insert into rpc_time (uid, trace_id, ",
        "where_happen, is_success, ",
        "start_timestamp, end_timestamp, ",
        "serialize, deserialize, ",
        "total, get_connect, ",
        "load_balance, wait_for_response, ",
        "get_server_list, handle)",
        "values (#{uid,jdbcType=BIGINT}, #{traceId,jdbcType=CHAR}, ",
        "#{whereHappen,jdbcType=CHAR}, #{isSuccess,jdbcType=BIT}, ",
        "#{startTimestamp,jdbcType=BIGINT}, #{endTimestamp,jdbcType=BIGINT}, ",
        "#{serialize,jdbcType=INTEGER}, #{deserialize,jdbcType=INTEGER}, ",
        "#{total,jdbcType=BIGINT}, #{getConnect,jdbcType=INTEGER}, ",
        "#{loadBalance,jdbcType=INTEGER}, #{waitForResponse,jdbcType=INTEGER}, ",
        "#{getServerList,jdbcType=INTEGER}, #{handle,jdbcType=INTEGER})"
    })
    int insert(Time record);

    @InsertProvider(type=TimeSqlProvider.class, method="insertSelective")
    int insertSelective(Time record);

    @SelectProvider(type=TimeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="trace_id", property="traceId", jdbcType=JdbcType.CHAR),
        @Result(column="where_happen", property="whereHappen", jdbcType=JdbcType.CHAR),
        @Result(column="is_success", property="isSuccess", jdbcType=JdbcType.BIT),
        @Result(column="start_timestamp", property="startTimestamp", jdbcType=JdbcType.BIGINT),
        @Result(column="end_timestamp", property="endTimestamp", jdbcType=JdbcType.BIGINT),
        @Result(column="serialize", property="serialize", jdbcType=JdbcType.INTEGER),
        @Result(column="deserialize", property="deserialize", jdbcType=JdbcType.INTEGER),
        @Result(column="total", property="total", jdbcType=JdbcType.BIGINT),
        @Result(column="get_connect", property="getConnect", jdbcType=JdbcType.INTEGER),
        @Result(column="load_balance", property="loadBalance", jdbcType=JdbcType.INTEGER),
        @Result(column="wait_for_response", property="waitForResponse", jdbcType=JdbcType.INTEGER),
        @Result(column="get_server_list", property="getServerList", jdbcType=JdbcType.INTEGER),
        @Result(column="handle", property="handle", jdbcType=JdbcType.INTEGER)
    })
    List<Time> selectByExample(TimeExample example);

    @Select({
        "select",
        "uid, trace_id, where_happen, is_success, start_timestamp, end_timestamp, serialize, ",
        "deserialize, total, get_connect, load_balance, wait_for_response, get_server_list, ",
        "handle",
        "from rpc_time",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="trace_id", property="traceId", jdbcType=JdbcType.CHAR),
        @Result(column="where_happen", property="whereHappen", jdbcType=JdbcType.CHAR),
        @Result(column="is_success", property="isSuccess", jdbcType=JdbcType.BIT),
        @Result(column="start_timestamp", property="startTimestamp", jdbcType=JdbcType.BIGINT),
        @Result(column="end_timestamp", property="endTimestamp", jdbcType=JdbcType.BIGINT),
        @Result(column="serialize", property="serialize", jdbcType=JdbcType.INTEGER),
        @Result(column="deserialize", property="deserialize", jdbcType=JdbcType.INTEGER),
        @Result(column="total", property="total", jdbcType=JdbcType.BIGINT),
        @Result(column="get_connect", property="getConnect", jdbcType=JdbcType.INTEGER),
        @Result(column="load_balance", property="loadBalance", jdbcType=JdbcType.INTEGER),
        @Result(column="wait_for_response", property="waitForResponse", jdbcType=JdbcType.INTEGER),
        @Result(column="get_server_list", property="getServerList", jdbcType=JdbcType.INTEGER),
        @Result(column="handle", property="handle", jdbcType=JdbcType.INTEGER)
    })
    Time selectByPrimaryKey(Long uid);

    @UpdateProvider(type=TimeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Time record, @Param("example") TimeExample example);

    @UpdateProvider(type=TimeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Time record, @Param("example") TimeExample example);

    @UpdateProvider(type=TimeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Time record);

    @Update({
        "update rpc_time",
        "set trace_id = #{traceId,jdbcType=CHAR},",
          "where_happen = #{whereHappen,jdbcType=CHAR},",
          "is_success = #{isSuccess,jdbcType=BIT},",
          "start_timestamp = #{startTimestamp,jdbcType=BIGINT},",
          "end_timestamp = #{endTimestamp,jdbcType=BIGINT},",
          "serialize = #{serialize,jdbcType=INTEGER},",
          "deserialize = #{deserialize,jdbcType=INTEGER},",
          "total = #{total,jdbcType=BIGINT},",
          "get_connect = #{getConnect,jdbcType=INTEGER},",
          "load_balance = #{loadBalance,jdbcType=INTEGER},",
          "wait_for_response = #{waitForResponse,jdbcType=INTEGER},",
          "get_server_list = #{getServerList,jdbcType=INTEGER},",
          "handle = #{handle,jdbcType=INTEGER}",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Time record);
}