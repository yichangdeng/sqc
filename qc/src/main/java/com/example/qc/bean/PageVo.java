package com.example.qc.bean;

import lombok.Data;

import java.util.List;

@Data
public class PageVo<T> {

    /**
     * 分页的数据
     */
    private List<T> data;
    /**
     * 数据总条数
     */
    private Long total;

    // 错误码
    private Integer code;

    // 提示信息
    private String msg;

}
