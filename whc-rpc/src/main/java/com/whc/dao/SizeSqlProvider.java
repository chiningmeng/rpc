package com.whc.dao;

import com.whc.dao.entity.Size;
import com.whc.dao.entity.SizeExample.Criteria;
import com.whc.dao.entity.SizeExample.Criterion;
import com.whc.dao.entity.SizeExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class SizeSqlProvider {
    public String countByExample(SizeExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("rpc_message_size");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(SizeExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("rpc_message_size");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(Size record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("rpc_message_size");
        
        if (record.getUid() != null) {
            sql.VALUES("uid", "#{uid,jdbcType=BIGINT}");
        }
        
        if (record.getTraceId() != null) {
            sql.VALUES("trace_id", "#{traceId,jdbcType=CHAR}");
        }
        
        if (record.getMessageType() != null) {
            sql.VALUES("message_type", "#{messageType,jdbcType=INTEGER}");
        }
        
        if (record.getMessageSize() != null) {
            sql.VALUES("message_size", "#{messageSize,jdbcType=BIGINT}");
        }
        
        return sql.toString();
    }

    public String selectByExample(SizeExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("uid");
        } else {
            sql.SELECT("uid");
        }
        sql.SELECT("trace_id");
        sql.SELECT("message_type");
        sql.SELECT("message_size");
        sql.FROM("rpc_message_size");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        Size record = (Size) parameter.get("record");
        SizeExample example = (SizeExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("rpc_message_size");
        
        if (record.getUid() != null) {
            sql.SET("uid = #{record.uid,jdbcType=BIGINT}");
        }
        
        if (record.getTraceId() != null) {
            sql.SET("trace_id = #{record.traceId,jdbcType=CHAR}");
        }
        
        if (record.getMessageType() != null) {
            sql.SET("message_type = #{record.messageType,jdbcType=INTEGER}");
        }
        
        if (record.getMessageSize() != null) {
            sql.SET("message_size = #{record.messageSize,jdbcType=BIGINT}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("rpc_message_size");
        
        sql.SET("uid = #{record.uid,jdbcType=BIGINT}");
        sql.SET("trace_id = #{record.traceId,jdbcType=CHAR}");
        sql.SET("message_type = #{record.messageType,jdbcType=INTEGER}");
        sql.SET("message_size = #{record.messageSize,jdbcType=BIGINT}");
        
        SizeExample example = (SizeExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(Size record) {
        SQL sql = new SQL();
        sql.UPDATE("rpc_message_size");
        
        if (record.getTraceId() != null) {
            sql.SET("trace_id = #{traceId,jdbcType=CHAR}");
        }
        
        if (record.getMessageType() != null) {
            sql.SET("message_type = #{messageType,jdbcType=INTEGER}");
        }
        
        if (record.getMessageSize() != null) {
            sql.SET("message_size = #{messageSize,jdbcType=BIGINT}");
        }
        
        sql.WHERE("uid = #{uid,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, SizeExample example, boolean includeExamplePhrase) {
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