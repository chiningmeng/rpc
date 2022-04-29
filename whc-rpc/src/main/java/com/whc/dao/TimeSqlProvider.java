package com.whc.dao;

import com.whc.dao.entity.Time;
import com.whc.dao.entity.TimeExample.Criteria;
import com.whc.dao.entity.TimeExample.Criterion;
import com.whc.dao.entity.TimeExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class TimeSqlProvider {
    public String countByExample(TimeExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("rpc_time");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(TimeExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("rpc_time");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Time record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("rpc_time");
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=BIGINT}");
        }
        
        if (record.getTraceId() != null) {
            sql.VALUES("trace_id", "#{traceId,jdbcType=CHAR}");
        }
        
        if (record.getWhereHappen() != null) {
            sql.VALUES("where_happen", "#{whereHappen,jdbcType=CHAR}");
        }
        
        if (record.getIsSuccess() != null) {
            sql.VALUES("is_success", "#{isSuccess,jdbcType=BIT}");
        }
        
        if (record.getStartTimestamp() != null) {
            sql.VALUES("start_timestamp", "#{startTimestamp,jdbcType=BIGINT}");
        }
        
        if (record.getEndTimestamp() != null) {
            sql.VALUES("end_timestamp", "#{endTimestamp,jdbcType=BIGINT}");
        }
        
        if (record.getSerialize() != null) {
            sql.VALUES("serialize", "#{serialize,jdbcType=INTEGER}");
        }
        
        if (record.getDeserialize() != null) {
            sql.VALUES("deserialize", "#{deserialize,jdbcType=INTEGER}");
        }
        
        if (record.getTotal() != null) {
            sql.VALUES("total", "#{total,jdbcType=BIGINT}");
        }
        
        if (record.getGetConnect() != null) {
            sql.VALUES("get_connect", "#{getConnect,jdbcType=INTEGER}");
        }
        
        if (record.getLoadBalance() != null) {
            sql.VALUES("load_balance", "#{loadBalance,jdbcType=INTEGER}");
        }
        
        if (record.getWaitForResponse() != null) {
            sql.VALUES("wait_for_response", "#{waitForResponse,jdbcType=INTEGER}");
        }
        
        if (record.getGetServerList() != null) {
            sql.VALUES("get_server_list", "#{getServerList,jdbcType=INTEGER}");
        }
        
        if (record.getHandle() != null) {
            sql.VALUES("handle", "#{handle,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String selectByExample(TimeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("uid");
        } else {
            sql.SELECT("uid");
        }
        sql.SELECT("trace_id");
        sql.SELECT("where_happen");
        sql.SELECT("is_success");
        sql.SELECT("start_timestamp");
        sql.SELECT("end_timestamp");
        sql.SELECT("serialize");
        sql.SELECT("deserialize");
        sql.SELECT("total");
        sql.SELECT("get_connect");
        sql.SELECT("load_balance");
        sql.SELECT("wait_for_response");
        sql.SELECT("get_server_list");
        sql.SELECT("handle");
        sql.FROM("rpc_time");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Time record = (Time) parameter.get("record");
        TimeExample example = (TimeExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("rpc_time");
        
        if (record.getUid() != null) {
            sql.SET("uid = #{record.uid,jdbcType=BIGINT}");
        }
        
        if (record.getTraceId() != null) {
            sql.SET("trace_id = #{record.traceId,jdbcType=CHAR}");
        }
        
        if (record.getWhereHappen() != null) {
            sql.SET("where_happen = #{record.whereHappen,jdbcType=CHAR}");
        }
        
        if (record.getIsSuccess() != null) {
            sql.SET("is_success = #{record.isSuccess,jdbcType=BIT}");
        }
        
        if (record.getStartTimestamp() != null) {
            sql.SET("start_timestamp = #{record.startTimestamp,jdbcType=BIGINT}");
        }
        
        if (record.getEndTimestamp() != null) {
            sql.SET("end_timestamp = #{record.endTimestamp,jdbcType=BIGINT}");
        }
        
        if (record.getSerialize() != null) {
            sql.SET("serialize = #{record.serialize,jdbcType=INTEGER}");
        }
        
        if (record.getDeserialize() != null) {
            sql.SET("deserialize = #{record.deserialize,jdbcType=INTEGER}");
        }
        
        if (record.getTotal() != null) {
            sql.SET("total = #{record.total,jdbcType=BIGINT}");
        }
        
        if (record.getGetConnect() != null) {
            sql.SET("get_connect = #{record.getConnect,jdbcType=INTEGER}");
        }
        
        if (record.getLoadBalance() != null) {
            sql.SET("load_balance = #{record.loadBalance,jdbcType=INTEGER}");
        }
        
        if (record.getWaitForResponse() != null) {
            sql.SET("wait_for_response = #{record.waitForResponse,jdbcType=INTEGER}");
        }
        
        if (record.getGetServerList() != null) {
            sql.SET("get_server_list = #{record.getServerList,jdbcType=INTEGER}");
        }
        
        if (record.getHandle() != null) {
            sql.SET("handle = #{record.handle,jdbcType=INTEGER}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("rpc_time");
        
        sql.SET("uid = #{record.uid,jdbcType=BIGINT}");
        sql.SET("trace_id = #{record.traceId,jdbcType=CHAR}");
        sql.SET("where_happen = #{record.whereHappen,jdbcType=CHAR}");
        sql.SET("is_success = #{record.isSuccess,jdbcType=BIT}");
        sql.SET("start_timestamp = #{record.startTimestamp,jdbcType=BIGINT}");
        sql.SET("end_timestamp = #{record.endTimestamp,jdbcType=BIGINT}");
        sql.SET("serialize = #{record.serialize,jdbcType=INTEGER}");
        sql.SET("deserialize = #{record.deserialize,jdbcType=INTEGER}");
        sql.SET("total = #{record.total,jdbcType=BIGINT}");
        sql.SET("get_connect = #{record.getConnect,jdbcType=INTEGER}");
        sql.SET("load_balance = #{record.loadBalance,jdbcType=INTEGER}");
        sql.SET("wait_for_response = #{record.waitForResponse,jdbcType=INTEGER}");
        sql.SET("get_server_list = #{record.getServerList,jdbcType=INTEGER}");
        sql.SET("handle = #{record.handle,jdbcType=INTEGER}");
        
        TimeExample example = (TimeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Time record) {
        SQL sql = new SQL();
        sql.UPDATE("rpc_time");
        
        if (record.getTraceId() != null) {
            sql.SET("trace_id = #{traceId,jdbcType=CHAR}");
        }
        
        if (record.getWhereHappen() != null) {
            sql.SET("where_happen = #{whereHappen,jdbcType=CHAR}");
        }
        
        if (record.getIsSuccess() != null) {
            sql.SET("is_success = #{isSuccess,jdbcType=BIT}");
        }
        
        if (record.getStartTimestamp() != null) {
            sql.SET("start_timestamp = #{startTimestamp,jdbcType=BIGINT}");
        }
        
        if (record.getEndTimestamp() != null) {
            sql.SET("end_timestamp = #{endTimestamp,jdbcType=BIGINT}");
        }
        
        if (record.getSerialize() != null) {
            sql.SET("serialize = #{serialize,jdbcType=INTEGER}");
        }
        
        if (record.getDeserialize() != null) {
            sql.SET("deserialize = #{deserialize,jdbcType=INTEGER}");
        }
        
        if (record.getTotal() != null) {
            sql.SET("total = #{total,jdbcType=BIGINT}");
        }
        
        if (record.getGetConnect() != null) {
            sql.SET("get_connect = #{getConnect,jdbcType=INTEGER}");
        }
        
        if (record.getLoadBalance() != null) {
            sql.SET("load_balance = #{loadBalance,jdbcType=INTEGER}");
        }
        
        if (record.getWaitForResponse() != null) {
            sql.SET("wait_for_response = #{waitForResponse,jdbcType=INTEGER}");
        }
        
        if (record.getGetServerList() != null) {
            sql.SET("get_server_list = #{getServerList,jdbcType=INTEGER}");
        }
        
        if (record.getHandle() != null) {
            sql.SET("handle = #{handle,jdbcType=INTEGER}");
        }
        
        sql.WHERE("uid = #{uid,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, TimeExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}