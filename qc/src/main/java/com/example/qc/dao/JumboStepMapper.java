package com.example.qc.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.qc.bean.JumboStepEntity;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface JumboStepMapper extends BaseMapper<JumboStepEntity> {

    //清空指定表
    @Update("truncate table jumbo_step_entity")
    int deleteJumboStep();
}
