package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.Region;
import com.atguigu.ssyx.vo.sys.RegionVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
public interface RegionService extends IService<Region> {


    List<Region> findReginByKeyword(String keyword);
}
