package com.whc.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseCodeEnum {

    SUCCESS(200, "成功"),
    FAIL(500, "失败");

    private final int code;
    private final String message;

}
