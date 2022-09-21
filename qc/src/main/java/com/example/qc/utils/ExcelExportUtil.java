package com.example.qc.utils;
import com.example.qc.bean.ExcelExp;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ExcelExportUtil {

    private ExcelExportUtil (){}
    private static ExcelExportUtil excelExportUtil = null;
    static{
        /** 类加载时创建，只会创建一个对象 */
        if(excelExportUtil == null) excelExportUtil = new ExcelExportUtil ();
    }

    /**
     * @param @param file 导出文件路径
     * @param @param mysheets
     * @return void
     * @throws
     * @Title: exportManySheetExcel
     * @Description: 可生成单个、多个sheet
     */
    public static HSSFWorkbook exportManySheetExcel(List<ExcelExp> mysheets) {

        // 创建工作薄
        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFWorkbook wb = new HSSFWorkbook();
        List<ExcelExp> sheets = mysheets;

        // 表头样式
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER); // 创建一个居中格式
        // 字体样式
        Font fontStyle = wb.createFont();
        fontStyle.setFontName("微软雅黑");
        fontStyle.setFontHeightInPoints((short) 12);
        // fontStyle.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        style.setFont(fontStyle);

        for (ExcelExp excel : sheets) {
            // 新建一个sheet
            HSSFSheet sheet = wb.createSheet(excel.getFileName());// 获取该sheet名称

            String[] handers = excel.getHanders();// 获取sheet的标题名

            HSSFRow tableName = sheet.createRow(0);
            HSSFCell cellName = tableName.createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
            CellStyle titleStyle = wb.createCellStyle();
            // 设置单元格样式
            Font titleFont = wb.createFont(); // 标题字体
            titleFont.setFontHeightInPoints((short) 16); // 字号
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);

            cellName.setCellStyle(titleStyle);
            // 设置单元格内容
            cellName.setCellValue(excel.getTableName());

            HSSFRow rowFirst = sheet.createRow(1);// 第一个sheet的第一行为标题
// 写标题
            for (int i = 0; i < handers.length; i++) {
                // 获取第一行的每个单元格
                HSSFCell cell = rowFirst.createCell(i);
                // 往单元格里写数据
                cell.setCellValue(handers[i]);
                cell.setCellStyle(style); // 加样式
                sheet.setColumnWidth(i, 4000); // 设置每列的列宽
            }

            // 写数据集
            List<ArrayList<String>> dataset = excel.getDataset();
            for (int i = 0; i < dataset.size(); i++) {
                List<String> data = dataset.get(i);// 获取该对象

                // 创建数据行
                HSSFRow row = sheet.createRow(i + 2);

                for (int j = 0; j < data.size(); j++) {
                    // 设置对应单元格的值
                    row.createCell(j).setCellValue(data.get(j));
                }
            }
        }
        return wb;
    }

    public static void outputXls(Workbook workbook, String fileName, HttpServletResponse response,
                                 HttpServletRequest request) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            workbook.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + encodeFileName(fileName + ".xls", request));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static String encodeFileName(String fileName, HttpServletRequest request)
            throws UnsupportedEncodingException {
        String agent = request.getHeader("USER-AGENT");
        if (null != agent && -1 != agent.indexOf("MSIE")) {
            return URLEncoder.encode(fileName, "UTF-8");
        } else if (null != agent && -1 != agent.indexOf("Mozilla")) {
            return "=?UTF-8?B?"
                    + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(fileName.getBytes("UTF-8")))) + "?=";
        } else {
            return fileName;
        }
    }

    /**
     * 把list<map>封装成list<String[]> 由于我的结果集是List<Map<String,Object>>,所以我写了这个个方法,把它转换成List<String[]>
     *
     * @param list   要封装的list
     * @param strKey String[]的长度
     * @return
     */
    public static List<String[]> listUtils(List<Map<String, Object>> list, String[] strKey) {

        if (list != null && list.size() > 0) {// 如果list有值

            List<String[]> strList = new ArrayList<String[]>();// 实例化一个list<string[]>

            for (Map<String, Object> map : list) {// 遍历数组

                String[] str = new String[strKey.length];// 实力一个string[]

                Integer count = 0;// 作为str的下标,每次从0开始

                for (String s : strKey) {// 遍历map中的key
                    if (map.get(s) != null) {
                        str[count] = map.get(s).toString();
                    } else {
                        str[count] = "";
                    }
                    // 把map的value赋值到str数组中
                    count++;// str的下标+1
                }

                if (str != null) {// 如果str有值,添加到strList
                    strList.add(str);
                }
            }
            if (strList != null && strList.size() > 0) {// 如果strList有值,返回strList
                return strList;
            }
        }

        return null;
    }
}