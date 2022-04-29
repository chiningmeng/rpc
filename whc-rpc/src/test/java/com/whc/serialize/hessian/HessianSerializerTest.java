package com.whc.serialize.hessian;

import com.whc.remoting.dto.Request;
import com.whc.remoting.serialize.hessian.HessianSerializer;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HessianSerializerTest {

    @Test
    public void hessianSerializerTest() {
        Request target = Request.builder().methodName("add")
                .parameters(new Object[]{"14", "5748"})
                .serviceClassName("com.whc.service")
                .paramTypes(new Class<?>[]{String.class, String.class})
                .requestId(UUID.randomUUID().toString())
                .build();
        HessianSerializer hessianSerializer = new HessianSerializer();
        byte[] bytes = hessianSerializer.serialize(target);
        Request actual = hessianSerializer.deserialize(bytes, Request.class);
        assertEquals(target.getRequestId(), actual.getRequestId());
    }
}