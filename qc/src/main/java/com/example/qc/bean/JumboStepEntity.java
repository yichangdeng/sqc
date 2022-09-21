package com.example.qc.bean;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JumboStepEntity implements Serializable {

    private Date testDate;

    private String sampleName;

    private String lotNo;

    private String jumboNo;

    private String step_21;

    private String step_41;

    private String de;

    private String l;

    private String a;

    private String b;

    private Date createTime;

}
