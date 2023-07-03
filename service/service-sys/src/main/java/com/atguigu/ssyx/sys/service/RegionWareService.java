package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 城市仓库关联表 服务类
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
public interface RegionWareService extends IService<RegionWare> {

    IPage<RegionWare> selectPage(Page<RegionWare> page, RegionWareQueryVo regionWareQueryVo);

    void saveRegionWare(RegionWare regionWare);

    void updateStatus(Long id, Integer status);
}
