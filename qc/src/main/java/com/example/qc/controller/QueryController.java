package com.example.qc.controller;

import com.example.qc.bean.JumboStepEntity;
import com.example.qc.bean.PageVo;
import com.example.qc.bean.WarningEntity;
import com.example.qc.service.WarningIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/query")
public class QueryController {

    @Autowired
    private WarningIService warningIservice;

    @GetMapping("/step_info")
    public PageVo<WarningEntity> queryPage (@RequestParam("params") String param,@RequestParam("pageNum") int pageNum,@RequestParam("pageSize") int pageSize) {
        PageVo<WarningEntity> pageVo = new PageVo<>();
        if (param == null || param.equals("")) {
            pageVo.setCode(500);
            pageVo.setMsg("参数不能为空");
            return pageVo;
        }
        // 查询jumbo光阶数据
        if (param.equals("1-1")) {
            pageVo = warningIservice.selectPage(pageNum, pageSize);
            if (pageVo != null) {
                pageVo.setCode(200);
                pageVo.setMsg("返回数据成功");
                return pageVo;
            }
        }
        // 查询胶液光阶数据
        if (param.equals("1-2")) {

        }
        return null;
    }
}
