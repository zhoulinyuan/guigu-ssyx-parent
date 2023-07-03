package com.atguigu.ssyx.activity.service.impl;

import com.atguigu.ssyx.activity.mapper.ActivityInfoMapper;
import com.atguigu.ssyx.activity.mapper.ActivityRuleMapper;
import com.atguigu.ssyx.activity.mapper.ActivitySkuMapper;
import com.atguigu.ssyx.activity.service.ActivityInfoService;
import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author John
* @description 针对表【activity_info(活动表)】的数据库操作Service实现
* @createDate 2023-06-28 14:15:19
*/
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo>
    implements ActivityInfoService {

    @Autowired
    private ActivityRuleMapper activityRuleMapper;
    @Autowired
    private ActivitySkuMapper activitySkuMapper;
    @Autowired
    private ProductFeignClient productFeignClient;

    @Override
    public IPage<ActivityInfo> selectPage(Page<ActivityInfo> pageParam) {

        IPage<ActivityInfo> page = baseMapper.selectPage(pageParam, null);
        List<ActivityInfo> activityInfoList = page.getRecords();
        activityInfoList.stream().forEach(item -> {
            item.setActivityTypeString(item.getActivityType().getComment());
        });
        return page;
    }

    @Override
    public Map<String, Object> findActivityRuleList(Long id) {
        HashMap<String, Object> result = new HashMap<>();

        //根据活动id查询，活动规则列表 activity_rule
        LambdaQueryWrapper<ActivityRule> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ActivityRule::getActivityId, id);
        List<ActivityRule> activityRules = activityRuleMapper.selectList(wrapper);
        result.put("activityRuleList", activityRules);
        //根据活动id查询，活动参与商品列表 activity_sku
        List<ActivitySku> activitySkuList = activitySkuMapper.selectList(new LambdaQueryWrapper<ActivitySku>().eq(ActivitySku::getActivityId, id));
        //获取所有的skuId
        List<Long> skuIdList = activitySkuList.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        System.out.println("skuIdList= "+skuIdList);
        //远程调用service-product模块接口
        List<SkuInfo> skuInfoList = productFeignClient.findSkuInfoList(skuIdList);
        result.put("skuInfoList", skuInfoList);
        return result;
    }

    @Override
    public void saveActivityRule(ActivityRuleVo activityRuleVo) {

        Long activityId = activityRuleVo.getActivityId();
        //删除之前的规则
        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityId));

        activityRuleMapper.delete(new LambdaQueryWrapper<ActivityRule>().eq(ActivityRule::getActivityId, activityId));
        //保存活动规则
        List<ActivityRule> activityRuleList = activityRuleVo.getActivityRuleList();
        ActivityInfo activityInfo = baseMapper.selectById(activityId);
        activityRuleList.stream().forEach(item -> {
            item.setActivityId(activityId);
            item.setActivityType(activityInfo.getActivityType());
            activityRuleMapper.insert(item);
        });
        //保存活动参与商品
        List<ActivitySku> activitySkuList = activityRuleVo.getActivitySkuList();
        activitySkuList.stream().forEach(item -> {
            item.setActivityId(activityId);
            activitySkuMapper.insert(item);
        });
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {

        //1 根据关键字sku匹配内容列表
        ///远程调用
        List<SkuInfo> skuInfoByKeyword = productFeignClient.findSkuInfoByKeyword(keyword);
        if (skuInfoByKeyword.size() == 0) {
            return skuInfoByKeyword;
        }
        List<Long> idList = skuInfoByKeyword.stream().map(SkuInfo::getId).collect(Collectors.toList());
        //2 判断商品是否参加过
        List<Long> existSkuIdList=baseMapper.selectSkuIdListExist(idList);
        //3 过滤掉参加过的商品
        List<SkuInfo> skuInfoList = skuInfoByKeyword.stream().filter(item -> {
            return !existSkuIdList.contains(item.getId());
        }).collect(Collectors.toList());
        return skuInfoList;
    }
}




