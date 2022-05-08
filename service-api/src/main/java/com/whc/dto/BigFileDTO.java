package com.whc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class BigFileDTO implements Serializable {
    private String bigFile;
}
