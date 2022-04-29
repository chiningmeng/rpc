package com.whc.dao;

import com.whc.dao.entity.Size;
import com.whc.dao.entity.SizeExample;
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

public interface SizeMapper {
    @SelectProvider(type=SizeSqlProvider.class, method="countByExample")
    long countByExample(SizeExample example);

    @DeleteProvider(type=SizeSqlProvider.class, method="deleteByExample")
    int deleteByExample(SizeExample example);

    @Delete({
        "delete from rpc_message_size",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long uid);

    @Insert({
        "insert into rpc_message_size (uid, trace_id, ",
        "message_type, message_size)",
        "values (#{uid,jdbcType=BIGINT}, #{traceId,jdbcType=CHAR}, ",
        "#{messageType,jdbcType=INTEGER}, #{messageSize,jdbcType=BIGINT})"
    })
    int insert(Size record);

    @InsertProvider(type=SizeSqlProvider.class, method="insertSelective")
    int insertSelective(Size record);

    @SelectProvider(type=SizeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="trace_id", property="traceId", jdbcType=JdbcType.CHAR),
        @Result(column="message_type", property="messageType", jdbcType=JdbcType.INTEGER),
        @Result(column="message_size", property="messageSize", jdbcType=JdbcType.BIGINT)
    })
    List<Size> selectByExample(SizeExample example);

    @Select({
        "select",
        "uid, trace_id, message_type, message_size",
        "from rpc_message_size",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="uid", property="uid", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="trace_id", property="traceId", jdbcType=JdbcType.CHAR),
        @Result(column="message_type", property="messageType", jdbcType=JdbcType.INTEGER),
        @Result(column="message_size", property="messageSize", jdbcType=JdbcType.BIGINT)
    })
    Size selectByPrimaryKey(Long uid);

    @UpdateProvider(type=SizeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Size record, @Param("example") SizeExample example);

    @UpdateProvider(type=SizeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Size record, @Param("example") SizeExample example);

    @UpdateProvider(type=SizeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Size record);

    @Update({
        "update rpc_message_size",
        "set trace_id = #{traceId,jdbcType=CHAR},",
          "message_type = #{messageType,jdbcType=INTEGER},",
          "message_size = #{messageSize,jdbcType=BIGINT}",
        "where uid = #{uid,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Size record);
}