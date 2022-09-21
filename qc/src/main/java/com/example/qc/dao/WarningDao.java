package com.example.qc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.qc.bean.WarningEntity;
import com.example.qc.bean.WarningVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface WarningDao extends BaseMapper {

    // 分页查询
    IPage<WarningEntity> selectPage(Page<WarningEntity> page);

    // 分页查询所有预警数据
    IPage<WarningEntity> selectWarningPage(Page<WarningEntity> page);

    // 根据品名查询预警数据
    List<WarningVo> selectListBySampleName(String sampleName);

}
