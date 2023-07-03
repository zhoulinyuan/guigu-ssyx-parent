package com.atguigu.ssyx.product.api;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.product.service.CategoryService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductInnerController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuInfoService skuInfoService;
    //根据CategoryId查询分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId){
        Category category = categoryService.getById(categoryId);

        return category;
    }
    //根据skuId查询sku信息
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfoById(@PathVariable Long skuId){
        SkuInfo skuInfo = skuInfoService.getById(skuId);

        return skuInfo;
    }

    //根据skuid列表得到sku信息列表
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList){
        List<SkuInfo> skuInfoList = skuInfoService.findSkuInfoList(skuIdList);
        return skuInfoList;
    }

    //根据关键字匹配sku列表
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable String keyword){
        List<SkuInfo> skuInfoList = skuInfoService.findSkuInfoByKeyword(keyword);
        return skuInfoList;
    }
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList){
        List<Category> categoryList = categoryService.listByIds(categoryIdList);
        return categoryList;
    }

    //获取所有分类
    @GetMapping("inner/findAllCategoryList")
    public List<Category> findAllCategoryList(){
        List<Category> categoryList = categoryService.list();
        return categoryList;
    }

    //获取新人专享商品
    @GetMapping("inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList(){
        List<SkuInfo> skuInfoList = skuInfoService.findNewPersonSkuInfoList();
        return skuInfoList;
    }
}
