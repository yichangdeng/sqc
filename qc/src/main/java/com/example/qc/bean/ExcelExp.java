package com.example.qc.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExcelExp {

    private String fileName;// sheet的名称

    private String[] handers = {"品名","LOT号","JUMBO号","预警信息Info"};// sheet里的标题

    private List<ArrayList<String>> dataset;// sheet里的数据集

    private String tableName = "预警信息";

}
