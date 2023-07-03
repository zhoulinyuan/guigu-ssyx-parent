package com.atguigu.ssyx.activity.controller;

import com.atguigu.ssyx.activity.service.CouponInfoService;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/activity/couponInfo")
@CrossOrigin
public class CouponInfoController {
    @Autowired
    private CouponInfoService couponInfoService;

    @GetMapping("{page}/{limit}")
    public Result getPageList(@PathVariable Long page,
                              @PathVariable Long limit
                              ){
        Page<CouponInfo> pageParam = new Page<>(page,limit);
        IPage<CouponInfo> page2= couponInfoService.selectPage(pageParam);
        return Result.ok(page2);
    }
    @PostMapping("save")
    public Result save(@RequestBody CouponInfo couponInfo) {
        couponInfoService.save(couponInfo);
        return Result.ok(null);
    }
    @GetMapping("get/{id}")
    public Result getByIdd(@PathVariable Long id) {
        CouponInfo couponInfo = couponInfoService.getById(id);
        couponInfo.setCouponTypeString(couponInfo.getCouponType().getComment());
        if(couponInfo.getRangeType() != null){
            couponInfo.setRangeTypeString(couponInfo.getRangeType().getComment());
        }
        return Result.ok(couponInfo);
    }
    @GetMapping("findCouponRuleList/{id}")
    public Result findCouponRuleList(@PathVariable Long id){
        Map<String,Object> map=couponInfoService.findCouponRuleList(id);
        return Result.ok(null);
    }

    @PostMapping("saveCouponRule")
    public Result saveCouponRule(@RequestBody CouponRuleVo couponRuleVo){
        couponInfoService.saveCouponRule(couponRuleVo);
        return Result.ok(null);
    }
}
