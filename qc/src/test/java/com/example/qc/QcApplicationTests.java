package com.example.qc;

import com.example.qc.bean.JumboStepEntity;
import com.example.qc.dao.JumboStepMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;

@SpringBootTest
class QcApplicationTests {

    @Autowired
    private JumboStepMapper excelMapper;

    @Test
    void contextLoads() {

        // 通过构造器查询一个list
//        List<ExcelEntity> list = excelMapper.selectList(null);
//        list.forEach(System.out::println);

    }

//    @Test
//    void excelLoads() {
//
//        File file = new File("C:\\Users\\yichang.deng\\Desktop\\QCTEMP_Q091.xlsx");
//        if (!file.exists()) {
//            System.out.println("文件不存在！");
//        }
//        try {
//            InputStream in = new FileInputStream(file);
//            // 获取整张EXCEL
//            Workbook excel = new XSSFWorkbook(in);
//            // 获取第一个sheet
//            Sheet sheet = excel.getSheetAt(0);
//
//            // 创建对象
//            JumboStepEntity excelEntity = new JumboStepEntity();
//
//            for (int i = 1; i <=sheet.getLastRowNum() ; i++) {
//
//                Row row = sheet.getRow(i);
//                // 获取检测日期
//                Cell cell1 = row.getCell(1);
//                if (cell1 != null) {
//                    excelEntity.setTestDate(cell1.getDateCellValue());
//                }
//                // 获取品名
//                Cell cell4 = row.getCell(4);
//                if (cell4 != null) {
//                    cell4.setCellType(CellType.STRING);
//                    excelEntity.setSampleName(cell4.getStringCellValue());
//                }
//
//                // 获取批号
//                Cell cell5 = row.getCell(5);
//                if (cell5 != null) {
//                    cell5.setCellType(CellType.STRING);
//                    excelEntity.setLotNo(cell5.getStringCellValue());
//                }
//                // 获取jumbo号
//                Cell cell6 = row.getCell(6);
//                if (cell6 != null) {
//                    cell6.setCellType(CellType.STRING);
//                    excelEntity.setJumboNo(cell6.getStringCellValue());
//                }
//                // 获取step_21
//                Cell cell19 = row.getCell(18);
//                if (cell19 != null) {
//                    cell19.setCellType(CellType.STRING);
//                    excelEntity.setStep_21(cell19.getStringCellValue());
//                }
//                // 获取step_41
//                Cell cell20 = row.getCell(19);
//                if (cell20 != null) {
//                    cell20.setCellType(CellType.STRING);
//                    excelEntity.setStep_41(cell20.getStringCellValue());
//                }
//
//                // 获取色差
//                Cell cell35 = row.getCell(35);
//                if (cell35 != null) {
//                    cell35.setCellType(CellType.STRING);
//                    excelEntity.setDe(cell35.getStringCellValue());
//                }
//                // 获取l*值
//                Cell cell36 = row.getCell(36);
//                if (cell36 != null) {
//                    cell36.setCellType(CellType.STRING);
//                    excelEntity.setL(cell36.getStringCellValue());
//                }
//                // 获取a*值
//                Cell cell37 = row.getCell(37);
//                if (cell37 != null) {
//                    cell37.setCellType(CellType.STRING);
//                    excelEntity.setA(cell37.getStringCellValue());
//                }
//                // 获取b*值
//                Cell cell38 = row.getCell(38);
//                if (cell38 != null) {
//                    cell38.setCellType(CellType.STRING);
//                    excelEntity.setB(cell38.getStringCellValue());
//                }
//                // 时间戳
//                excelEntity.setCreateTime(new Date());
//
//                // 插入数据库
//                excelMapper.insert(excelEntity);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
