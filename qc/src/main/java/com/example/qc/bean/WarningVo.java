package com.example.qc.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class WarningVo implements Serializable {

    private String sampleName;

    private String lotNo;

    private String jumboNo;

    private String warningInfo;

}
