package com.example.qc.service.serviceimpl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.qc.bean.*;
import com.example.qc.dao.WarningDao;
import com.example.qc.service.WarningIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class WarningServiceImpl implements WarningIService {

    @Autowired
    private WarningDao warningDao;

    @Override
    public PageVo<WarningEntity> selectPage(int current, int size) {
        Page page = new Page(current, size);
        IPage iPage = warningDao.selectWarningPage(page);

        ArrayList<WarningEntity> warningEntities = new ArrayList<>();
        List<WarningEntity> records = iPage.getRecords();

        PageVo<WarningEntity> pageVo = new PageVo<>();

        for (WarningEntity record : records) {
            // 判断中心偏离值(R值)
            String agvSizeCenterValue = record.getAgvSizeCenterValue();
            if (Double.parseDouble(agvSizeCenterValue) >= 1.5) {
                record.setAgvSizeCenterValue(agvSizeCenterValue+"==>长期偏离中心值上方");
            }
            if (Double.parseDouble(agvSizeCenterValue) <= -1.5) {
                record.setAgvSizeCenterValue(agvSizeCenterValue+"==>长期偏离中心值下方");
            }

            // 获取预警品名
            String sampleName = record.getSampleName();
            // 根据品名查询对应预警明细
            List<WarningVo> warningList = warningDao.selectListBySampleName(sampleName);
            record.setChildren(warningList);
            warningEntities.add(record);
        }
        pageVo.setData(warningEntities);
        pageVo.setTotal(iPage.getTotal());
        return pageVo;
    }

    @Override
    public List<ExcelExp> exportExcel(int pageNum, int pageSize) {

        // 根据当前页和页码大小获取对应数据
        PageVo<WarningEntity> page = selectPage(pageNum, pageSize);
        // 获取分页后的数据
        List<WarningEntity> PageData = page.getData();

        List<ExcelExp> list = new ArrayList<>();


        for (WarningEntity pageDatum : PageData) {

            List<ArrayList<String>> strArray = new ArrayList<>();

            ExcelExp excelExp = new ExcelExp();

            String sampleName = pageDatum.getSampleName();

            excelExp.setFileName(sampleName);

            List<WarningVo> children = pageDatum.getChildren();

            for (WarningVo child : children) {
                ArrayList<String> str = new ArrayList<>();
                str.add(child.getSampleName());
                str.add(child.getLotNo());
                str.add(child.getJumboNo());
                str.add(child.getWarningInfo());
                strArray.add(str);
            }
            excelExp.setDataset(strArray);

            list.add(excelExp);
        }
        return list;
    }
}
