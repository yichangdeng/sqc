package com.example.qc.controller;

import com.example.qc.bean.ExcelExp;
import com.example.qc.bean.JumboStepEntity;
import com.example.qc.bean.Result;
import com.example.qc.dao.JumboStepMapper;
import com.example.qc.service.WarningIService;
import com.example.qc.utils.ExcelExportUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class FileController {

    @Autowired
    private JumboStepMapper jumboStepMapper;

    @Autowired
    private WarningIService warningIService;

    // 单个文件上传
    @RequestMapping(value = "/single")
    public Result<JumboStepEntity> upload(@RequestParam("file") MultipartFile file) {    //注意参数

        Result<JumboStepEntity> res = new Result<JumboStepEntity>();

        // 插入数据库数据之前先清除数据库数据表
        int result = jumboStepMapper.deleteJumboStep();

        // 删除完数据后，开始插入数据
        if (result == 0) {
            try {
                if (file.isEmpty()) {
                    res.setCode(500);
                    res.setMsg("该文件为空");
                    return res;
                }
                InputStream in = file.getInputStream();
                // 获取整张EXCEL
                Workbook excel = new XSSFWorkbook(in);
                // 获取第一个sheet
                Sheet sheet = excel.getSheetAt(0);
                // 创建对象
                JumboStepEntity jumboStepEntityEntity = new JumboStepEntity();
                // 创建对象数组
                List<JumboStepEntity> jumboStepEntityEntityList = new ArrayList<>();

                for (int i = 1; i <=sheet.getLastRowNum() ; i++) {

                    Row row = sheet.getRow(i);
                    // 获取检测日期
                    Cell cell1 = row.getCell(1);
                    if (cell1 != null) {
                        jumboStepEntityEntity.setTestDate(cell1.getDateCellValue());
                    }
                    // 获取品名
                    Cell cell4 = row.getCell(4);
                    if (cell4 != null) {
                        cell4.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setSampleName(cell4.getStringCellValue());
                    }

                    // 获取批号
                    Cell cell5 = row.getCell(5);
                    if (cell5 != null) {
                        cell5.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setLotNo(cell5.getStringCellValue());
                    }
                    // 获取jumbo号
                    Cell cell6 = row.getCell(6);
                    if (cell6 != null) {
                        cell6.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setJumboNo(cell6.getStringCellValue());
                    }
                    // 获取step_21
                    Cell cell19 = row.getCell(18);
                    if (cell19 != null) {
                        cell19.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setStep_21(cell19.getStringCellValue());
                    }
                    // 获取step_41
                    Cell cell20 = row.getCell(19);
                    if (cell20 != null) {
                        cell20.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setStep_41(cell20.getStringCellValue());
                    }

                    // 获取色差
                    Cell cell35 = row.getCell(35);
                    if (cell35 != null) {
                        cell35.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setDe(cell35.getStringCellValue());
                    }
                    // 获取l*值
                    Cell cell36 = row.getCell(36);
                    if (cell36 != null) {
                        cell36.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setL(cell36.getStringCellValue());
                    }
                    // 获取a*值
                    Cell cell37 = row.getCell(37);
                    if (cell37 != null) {
                        cell37.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setA(cell37.getStringCellValue());
                    }
                    // 获取b*值
                    Cell cell38 = row.getCell(38);
                    if (cell38 != null) {
                        cell38.setCellType(CellType.STRING);
                        jumboStepEntityEntity.setB(cell38.getStringCellValue());
                    }
                    // 时间戳
                    jumboStepEntityEntity.setCreateTime(new Date());

                    jumboStepEntityEntityList.add(jumboStepEntityEntity);

                    // 插入数据库
                    int insert = jumboStepMapper.insert(jumboStepEntityEntity);

                    if (insert >0) {
                        res.setCode(200);
                        res.setMsg("文件上传成功");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            res.setCode(500);
            res.setMsg("数据表删除失败");
        }
        return res;
    }

    // 下载导出excel
    @RequestMapping(value = "/exportExcel",method = RequestMethod.GET)
    public void exportExcel(@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize,HttpServletResponse response) {
        List<ExcelExp> excelExps = warningIService.exportExcel(pageNum, pageSize);

        // 使用Excel工具类
        HSSFWorkbook workbook = ExcelExportUtil.exportManySheetExcel(excelExps);

        try {
            // 清除缓存
            response.reset();
            // 文件名称
            String fileName = "预警信息.xls";

            fileName = new String(fileName.getBytes(), "ISO-8859-1");
            // 跨域
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            ServletOutputStream out = response.getOutputStream();
            //这时候把创建好的excel写入到输出流
            workbook.write(out);

            //养成好习惯，出门记得随手关门
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}