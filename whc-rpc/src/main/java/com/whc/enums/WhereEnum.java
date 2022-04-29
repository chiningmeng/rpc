package com.whc.enums;

import com.whc.remoting.constants.RpcConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum WhereEnum {

    Client(RpcConstants.REQUEST_TYPE,"Client"),
    Server(RpcConstants.RESPONSE_TYPE,"Server");

    private int id;
    private String name;


}
