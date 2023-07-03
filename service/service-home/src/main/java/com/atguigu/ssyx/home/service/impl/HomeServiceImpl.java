package com.atguigu.ssyx.home.service.impl;

import com.atguigu.ssyx.client.product.ProductFeignClient;
import com.atguigu.ssyx.client.search.SkuFeignClient;
import com.atguigu.ssyx.client.user.UserFeignClient;
import com.atguigu.ssyx.home.service.HomeService;
import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.search.SkuEs;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private ProductFeignClient productFeignClient;
    @Autowired
    private SkuFeignClient skuFeignClient;
    @Override
    public Map<String, Object> homeData(Long userId) {
        Map<String,Object> result = new HashMap<>();
        LeaderAddressVo userAddressByUserId = userFeignClient.getUserAddressByUserId(userId);
        result.put("leaderAddressVo",userAddressByUserId);

        List<Category> allCategoryList = productFeignClient.findAllCategoryList();
        result.put("categoryList",allCategoryList);

        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        result.put("newPersonSkuInfoList",newPersonSkuInfoList);

        List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
        result.put("hotSkuList",hotSkuList);
        return null;
    }
}
