package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.service.RegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
@RestController
@RequestMapping("/admin/sys/regionWare")
@CrossOrigin
public class RegionWareController {
    @Autowired
    private RegionWareService regionWareService;
    //开通区域列表
    @GetMapping("{currentPage}/{limit}")
    public Result getPageList(@PathVariable Long currentPage,
                              @PathVariable Long limit,
                                RegionWareQueryVo regionWareQueryVo){
        Page<RegionWare> page = new Page<>(currentPage, limit);
        IPage<RegionWare> pageList= regionWareService.selectPage(page,regionWareQueryVo);
        return Result.ok(pageList);
    }
    //添加开通区域
    @PostMapping("save")
    public Result save(@RequestBody RegionWare regionWare){
        regionWareService.saveRegionWare(regionWare);
        return Result.ok(null);
    }
    //删除开通区域
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        regionWareService.removeById(id);
        return Result.ok(null);
    }
    //取消开通区域
    @PostMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status){
        regionWareService.updateStatus(id,status);
        return Result.ok(null);
    }

}

