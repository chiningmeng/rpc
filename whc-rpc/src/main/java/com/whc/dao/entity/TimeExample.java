package com.whc.dao.entity;

import java.util.ArrayList;
import java.util.List;

public class TimeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TimeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Long value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Long value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Long value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Long value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Long value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Long value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Long> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Long> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Long value1, Long value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Long value1, Long value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andTraceIdIsNull() {
            addCriterion("trace_id is null");
            return (Criteria) this;
        }

        public Criteria andTraceIdIsNotNull() {
            addCriterion("trace_id is not null");
            return (Criteria) this;
        }

        public Criteria andTraceIdEqualTo(String value) {
            addCriterion("trace_id =", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotEqualTo(String value) {
            addCriterion("trace_id <>", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThan(String value) {
            addCriterion("trace_id >", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdGreaterThanOrEqualTo(String value) {
            addCriterion("trace_id >=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThan(String value) {
            addCriterion("trace_id <", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLessThanOrEqualTo(String value) {
            addCriterion("trace_id <=", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdLike(String value) {
            addCriterion("trace_id like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotLike(String value) {
            addCriterion("trace_id not like", value, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdIn(List<String> values) {
            addCriterion("trace_id in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotIn(List<String> values) {
            addCriterion("trace_id not in", values, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdBetween(String value1, String value2) {
            addCriterion("trace_id between", value1, value2, "traceId");
            return (Criteria) this;
        }

        public Criteria andTraceIdNotBetween(String value1, String value2) {
            addCriterion("trace_id not between", value1, value2, "traceId");
            return (Criteria) this;
        }

        public Criteria andWhereHappenIsNull() {
            addCriterion("where_happen is null");
            return (Criteria) this;
        }

        public Criteria andWhereHappenIsNotNull() {
            addCriterion("where_happen is not null");
            return (Criteria) this;
        }

        public Criteria andWhereHappenEqualTo(String value) {
            addCriterion("where_happen =", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenNotEqualTo(String value) {
            addCriterion("where_happen <>", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenGreaterThan(String value) {
            addCriterion("where_happen >", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenGreaterThanOrEqualTo(String value) {
            addCriterion("where_happen >=", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenLessThan(String value) {
            addCriterion("where_happen <", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenLessThanOrEqualTo(String value) {
            addCriterion("where_happen <=", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenLike(String value) {
            addCriterion("where_happen like", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenNotLike(String value) {
            addCriterion("where_happen not like", value, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenIn(List<String> values) {
            addCriterion("where_happen in", values, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenNotIn(List<String> values) {
            addCriterion("where_happen not in", values, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenBetween(String value1, String value2) {
            addCriterion("where_happen between", value1, value2, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andWhereHappenNotBetween(String value1, String value2) {
            addCriterion("where_happen not between", value1, value2, "whereHappen");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIsNull() {
            addCriterion("is_success is null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIsNotNull() {
            addCriterion("is_success is not null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessEqualTo(Boolean value) {
            addCriterion("is_success =", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotEqualTo(Boolean value) {
            addCriterion("is_success <>", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessGreaterThan(Boolean value) {
            addCriterion("is_success >", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_success >=", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessLessThan(Boolean value) {
            addCriterion("is_success <", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessLessThanOrEqualTo(Boolean value) {
            addCriterion("is_success <=", value, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessIn(List<Boolean> values) {
            addCriterion("is_success in", values, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotIn(List<Boolean> values) {
            addCriterion("is_success not in", values, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessBetween(Boolean value1, Boolean value2) {
            addCriterion("is_success between", value1, value2, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andIsSuccessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_success not between", value1, value2, "isSuccess");
            return (Criteria) this;
        }

        public Criteria andStartTimestampIsNull() {
            addCriterion("start_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andStartTimestampIsNotNull() {
            addCriterion("start_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimestampEqualTo(Long value) {
            addCriterion("start_timestamp =", value, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampNotEqualTo(Long value) {
            addCriterion("start_timestamp <>", value, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampGreaterThan(Long value) {
            addCriterion("start_timestamp >", value, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampGreaterThanOrEqualTo(Long value) {
            addCriterion("start_timestamp >=", value, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampLessThan(Long value) {
            addCriterion("start_timestamp <", value, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampLessThanOrEqualTo(Long value) {
            addCriterion("start_timestamp <=", value, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampIn(List<Long> values) {
            addCriterion("start_timestamp in", values, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampNotIn(List<Long> values) {
            addCriterion("start_timestamp not in", values, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampBetween(Long value1, Long value2) {
            addCriterion("start_timestamp between", value1, value2, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andStartTimestampNotBetween(Long value1, Long value2) {
            addCriterion("start_timestamp not between", value1, value2, "startTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampIsNull() {
            addCriterion("end_timestamp is null");
            return (Criteria) this;
        }

        public Criteria andEndTimestampIsNotNull() {
            addCriterion("end_timestamp is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimestampEqualTo(Long value) {
            addCriterion("end_timestamp =", value, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampNotEqualTo(Long value) {
            addCriterion("end_timestamp <>", value, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampGreaterThan(Long value) {
            addCriterion("end_timestamp >", value, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampGreaterThanOrEqualTo(Long value) {
            addCriterion("end_timestamp >=", value, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampLessThan(Long value) {
            addCriterion("end_timestamp <", value, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampLessThanOrEqualTo(Long value) {
            addCriterion("end_timestamp <=", value, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampIn(List<Long> values) {
            addCriterion("end_timestamp in", values, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampNotIn(List<Long> values) {
            addCriterion("end_timestamp not in", values, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampBetween(Long value1, Long value2) {
            addCriterion("end_timestamp between", value1, value2, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andEndTimestampNotBetween(Long value1, Long value2) {
            addCriterion("end_timestamp not between", value1, value2, "endTimestamp");
            return (Criteria) this;
        }

        public Criteria andSerializeIsNull() {
            addCriterion("serialize is null");
            return (Criteria) this;
        }

        public Criteria andSerializeIsNotNull() {
            addCriterion("serialize is not null");
            return (Criteria) this;
        }

        public Criteria andSerializeEqualTo(Integer value) {
            addCriterion("serialize =", value, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeNotEqualTo(Integer value) {
            addCriterion("serialize <>", value, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeGreaterThan(Integer value) {
            addCriterion("serialize >", value, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeGreaterThanOrEqualTo(Integer value) {
            addCriterion("serialize >=", value, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeLessThan(Integer value) {
            addCriterion("serialize <", value, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeLessThanOrEqualTo(Integer value) {
            addCriterion("serialize <=", value, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeIn(List<Integer> values) {
            addCriterion("serialize in", values, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeNotIn(List<Integer> values) {
            addCriterion("serialize not in", values, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeBetween(Integer value1, Integer value2) {
            addCriterion("serialize between", value1, value2, "serialize");
            return (Criteria) this;
        }

        public Criteria andSerializeNotBetween(Integer value1, Integer value2) {
            addCriterion("serialize not between", value1, value2, "serialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeIsNull() {
            addCriterion("deserialize is null");
            return (Criteria) this;
        }

        public Criteria andDeserializeIsNotNull() {
            addCriterion("deserialize is not null");
            return (Criteria) this;
        }

        public Criteria andDeserializeEqualTo(Integer value) {
            addCriterion("deserialize =", value, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeNotEqualTo(Integer value) {
            addCriterion("deserialize <>", value, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeGreaterThan(Integer value) {
            addCriterion("deserialize >", value, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeGreaterThanOrEqualTo(Integer value) {
            addCriterion("deserialize >=", value, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeLessThan(Integer value) {
            addCriterion("deserialize <", value, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeLessThanOrEqualTo(Integer value) {
            addCriterion("deserialize <=", value, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeIn(List<Integer> values) {
            addCriterion("deserialize in", values, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeNotIn(List<Integer> values) {
            addCriterion("deserialize not in", values, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeBetween(Integer value1, Integer value2) {
            addCriterion("deserialize between", value1, value2, "deserialize");
            return (Criteria) this;
        }

        public Criteria andDeserializeNotBetween(Integer value1, Integer value2) {
            addCriterion("deserialize not between", value1, value2, "deserialize");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Long value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Long value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Long value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Long value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Long value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Long value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Long> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Long> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Long value1, Long value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Long value1, Long value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andGetConnectIsNull() {
            addCriterion("get_connect is null");
            return (Criteria) this;
        }

        public Criteria andGetConnectIsNotNull() {
            addCriterion("get_connect is not null");
            return (Criteria) this;
        }

        public Criteria andGetConnectEqualTo(Integer value) {
            addCriterion("get_connect =", value, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectNotEqualTo(Integer value) {
            addCriterion("get_connect <>", value, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectGreaterThan(Integer value) {
            addCriterion("get_connect >", value, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectGreaterThanOrEqualTo(Integer value) {
            addCriterion("get_connect >=", value, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectLessThan(Integer value) {
            addCriterion("get_connect <", value, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectLessThanOrEqualTo(Integer value) {
            addCriterion("get_connect <=", value, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectIn(List<Integer> values) {
            addCriterion("get_connect in", values, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectNotIn(List<Integer> values) {
            addCriterion("get_connect not in", values, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectBetween(Integer value1, Integer value2) {
            addCriterion("get_connect between", value1, value2, "getConnect");
            return (Criteria) this;
        }

        public Criteria andGetConnectNotBetween(Integer value1, Integer value2) {
            addCriterion("get_connect not between", value1, value2, "getConnect");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceIsNull() {
            addCriterion("load_balance is null");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceIsNotNull() {
            addCriterion("load_balance is not null");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceEqualTo(Integer value) {
            addCriterion("load_balance =", value, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceNotEqualTo(Integer value) {
            addCriterion("load_balance <>", value, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceGreaterThan(Integer value) {
            addCriterion("load_balance >", value, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceGreaterThanOrEqualTo(Integer value) {
            addCriterion("load_balance >=", value, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceLessThan(Integer value) {
            addCriterion("load_balance <", value, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceLessThanOrEqualTo(Integer value) {
            addCriterion("load_balance <=", value, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceIn(List<Integer> values) {
            addCriterion("load_balance in", values, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceNotIn(List<Integer> values) {
            addCriterion("load_balance not in", values, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceBetween(Integer value1, Integer value2) {
            addCriterion("load_balance between", value1, value2, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andLoadBalanceNotBetween(Integer value1, Integer value2) {
            addCriterion("load_balance not between", value1, value2, "loadBalance");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseIsNull() {
            addCriterion("wait_for_response is null");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseIsNotNull() {
            addCriterion("wait_for_response is not null");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseEqualTo(Integer value) {
            addCriterion("wait_for_response =", value, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseNotEqualTo(Integer value) {
            addCriterion("wait_for_response <>", value, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseGreaterThan(Integer value) {
            addCriterion("wait_for_response >", value, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseGreaterThanOrEqualTo(Integer value) {
            addCriterion("wait_for_response >=", value, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseLessThan(Integer value) {
            addCriterion("wait_for_response <", value, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseLessThanOrEqualTo(Integer value) {
            addCriterion("wait_for_response <=", value, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseIn(List<Integer> values) {
            addCriterion("wait_for_response in", values, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseNotIn(List<Integer> values) {
            addCriterion("wait_for_response not in", values, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseBetween(Integer value1, Integer value2) {
            addCriterion("wait_for_response between", value1, value2, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andWaitForResponseNotBetween(Integer value1, Integer value2) {
            addCriterion("wait_for_response not between", value1, value2, "waitForResponse");
            return (Criteria) this;
        }

        public Criteria andGetServerListIsNull() {
            addCriterion("get_server_list is null");
            return (Criteria) this;
        }

        public Criteria andGetServerListIsNotNull() {
            addCriterion("get_server_list is not null");
            return (Criteria) this;
        }

        public Criteria andGetServerListEqualTo(Integer value) {
            addCriterion("get_server_list =", value, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListNotEqualTo(Integer value) {
            addCriterion("get_server_list <>", value, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListGreaterThan(Integer value) {
            addCriterion("get_server_list >", value, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListGreaterThanOrEqualTo(Integer value) {
            addCriterion("get_server_list >=", value, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListLessThan(Integer value) {
            addCriterion("get_server_list <", value, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListLessThanOrEqualTo(Integer value) {
            addCriterion("get_server_list <=", value, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListIn(List<Integer> values) {
            addCriterion("get_server_list in", values, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListNotIn(List<Integer> values) {
            addCriterion("get_server_list not in", values, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListBetween(Integer value1, Integer value2) {
            addCriterion("get_server_list between", value1, value2, "getServerList");
            return (Criteria) this;
        }

        public Criteria andGetServerListNotBetween(Integer value1, Integer value2) {
            addCriterion("get_server_list not between", value1, value2, "getServerList");
            return (Criteria) this;
        }

        public Criteria andHandleIsNull() {
            addCriterion("handle is null");
            return (Criteria) this;
        }

        public Criteria andHandleIsNotNull() {
            addCriterion("handle is not null");
            return (Criteria) this;
        }

        public Criteria andHandleEqualTo(Integer value) {
            addCriterion("handle =", value, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleNotEqualTo(Integer value) {
            addCriterion("handle <>", value, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleGreaterThan(Integer value) {
            addCriterion("handle >", value, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle >=", value, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleLessThan(Integer value) {
            addCriterion("handle <", value, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleLessThanOrEqualTo(Integer value) {
            addCriterion("handle <=", value, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleIn(List<Integer> values) {
            addCriterion("handle in", values, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleNotIn(List<Integer> values) {
            addCriterion("handle not in", values, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleBetween(Integer value1, Integer value2) {
            addCriterion("handle between", value1, value2, "handle");
            return (Criteria) this;
        }

        public Criteria andHandleNotBetween(Integer value1, Integer value2) {
            addCriterion("handle not between", value1, value2, "handle");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}