package com.example.qc.bean;

import lombok.Data;

@Data
public class Result<T> {

    private String test;

    private Integer code;

    private String msg;
}
