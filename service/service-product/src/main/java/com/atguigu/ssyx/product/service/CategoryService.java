package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.Category;
import com.atguigu.ssyx.vo.product.CategoryQueryVo;
import com.atguigu.ssyx.vo.product.CategoryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 商品三级分类 服务类
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
public interface CategoryService extends IService<Category> {

    IPage<Category> getCategoryList(Page<Category> page1, CategoryQueryVo categoryQueryVo);
}
