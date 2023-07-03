package com.atguigu.ssyx.search.controller;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.search.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {
    @Autowired
    private SkuService skuService;

    //上架
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperSku(@PathVariable("skuId") Long skuId){
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    //下架
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable("skuId") Long skuId){
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }
    @GetMapping("inner/findHotSkuList")
    public List<SkuEs> findHotSkuList(){

        return skuService.findHotSkuList();
    }
}
