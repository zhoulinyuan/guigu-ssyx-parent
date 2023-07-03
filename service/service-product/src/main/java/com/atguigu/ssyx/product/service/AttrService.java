package com.atguigu.ssyx.product.service;


import com.atguigu.ssyx.model.product.Attr;
import com.atguigu.ssyx.model.product.AttrGroup;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
public interface AttrService extends IService<Attr> {

    List<Attr> getAttrId(Long groupId);
}
