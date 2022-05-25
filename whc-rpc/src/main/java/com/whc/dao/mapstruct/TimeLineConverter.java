package com.whc.dao.mapstruct;

import com.whc.dao.entity.Time;
import com.whc.monitor.time.ClientTimeLine;
import com.whc.monitor.time.ServerTimeLine;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeLineConverter {

    TimeLineConverter INSTANCE = Mappers.getMapper(TimeLineConverter.class);

    @Mapping(source = "where.name", target = "whereHappen")
    @Mapping(source = "serializeTime", target = "serialize")
    @Mapping(source = "deserializeTime", target = "deserialize")
    @Mapping(source = "totalTime", target = "total")
    @Mapping(source = "getConnectTime", target = "getConnect")
    @Mapping(source = "loadBalanceTime", target = "loadBalance")
    @Mapping(source = "waitForResponseTime", target = "waitForResponse")
    @Mapping(source = "getServerListTime", target = "getServerList")
    @Mapping(source = "ipAddress", target = "ipAddress")
    Time clientToTimePO(ClientTimeLine timeLine);

    @Mapping(source = "where.name", target = "whereHappen")
    @Mapping(source = "serializeTime", target = "serialize")
    @Mapping(source = "deserializeTime", target = "deserialize")
    @Mapping(source = "totalTime", target = "total")
    @Mapping(source = "handleTime", target = "handle")
    Time ServerToTimePO(ServerTimeLine timeLine);
}
