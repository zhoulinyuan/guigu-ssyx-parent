package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.sys.service.RegionService;
import com.atguigu.ssyx.vo.sys.RegionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
@RestController
@RequestMapping("/admin/sys/region")
@CrossOrigin
public class RegionController {
    @Autowired
    private RegionService regionService;
    //根据区域的关键字查询区域列表信息
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findReginByKeyword(@PathVariable String keyword){
        List<Region> list = regionService.findReginByKeyword(keyword);
        return Result.ok(list);
    }


}

