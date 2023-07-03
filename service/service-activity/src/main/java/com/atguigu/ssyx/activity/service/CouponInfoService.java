package com.atguigu.ssyx.activity.service;

import com.atguigu.ssyx.model.activity.CouponInfo;
import com.atguigu.ssyx.vo.activity.CouponRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author John
* @description 针对表【coupon_info(优惠券信息)】的数据库操作Service
* @createDate 2023-06-28 14:15:19
*/
public interface CouponInfoService extends IService<CouponInfo> {

    IPage<CouponInfo> selectPage(Page<CouponInfo> pageParam);

    Map<String, Object> findCouponRuleList(Long id);

    void saveCouponRule(CouponRuleVo couponRuleVo);
}
