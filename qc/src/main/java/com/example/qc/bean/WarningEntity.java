package com.example.qc.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WarningEntity implements Serializable {

    // 品名
    private String sampleName;

    // 检测数
    private Integer testNumb;

    // 41STEP-近半年均值
    private double halfYearAvg;

    // 41STEP-规格上下限
    private String sizeLimit;

    // 41STEP-管控上下限
    private String sizeLimitControl;

    // 41STEP-规格中心值
    private double sizeCenterValue;

    // 均值较规格中心偏离值
    private String agvSizeCenterValue;

    // 预警信息
    private List<WarningVo> children;

}
