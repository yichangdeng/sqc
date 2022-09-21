package com.example.qc.service;

import com.example.qc.bean.ExcelExp;
import com.example.qc.bean.PageVo;
import com.example.qc.bean.Result;
import com.example.qc.bean.WarningEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


public interface WarningIService {

    // 根据页码查询预警数据
    PageVo<WarningEntity> selectPage(int current, int size);

    // 下载预警数据，导出excel
    List<ExcelExp> exportExcel(int pageNum, int pageSize);

}
