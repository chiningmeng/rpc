package com.whc.rpc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {
    KYRO((byte) 0x01, "kyro"),
    PROTOSTUFF((byte) 0x02, "protostuff"),
    HESSIAN((byte) 0X03, "hessian");

    private final byte code;
    private String name;

    public static String getName(byte code) {
        return Arrays.stream(SerializationTypeEnum.values())
                .filter(e -> code == e.getCode()).findAny().orElse(null).getName();
    }
}

