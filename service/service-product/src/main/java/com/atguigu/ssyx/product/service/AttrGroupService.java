package com.atguigu.ssyx.product.service;


import com.atguigu.ssyx.model.product.AttrGroup;
import com.atguigu.ssyx.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
public interface AttrGroupService extends IService<AttrGroup> {

    IPage<AttrGroup> getAttrGroupList(Page<AttrGroup> page1, AttrGroupQueryVo attrGroupQueryVo);
}
