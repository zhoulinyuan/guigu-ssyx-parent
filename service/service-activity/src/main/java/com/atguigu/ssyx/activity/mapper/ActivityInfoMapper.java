package com.atguigu.ssyx.activity.mapper;


import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author John
* @description 针对表【activity_info(活动表)】的数据库操作Mapper
* @createDate 2023-06-28 14:15:19
* @Entity com.atguigu.ssyx.acl.ActivityInfo
*/
public interface ActivityInfoMapper extends BaseMapper<ActivityInfo> {


    /**
     * @description: 查询skuIdList中存在的skuId
     * @param: idList
     * @return: java.util.List<java.lang.Long>
     */
    List<Long> selectSkuIdListExist(@Param("skuIdList") List<Long> idList);


}




