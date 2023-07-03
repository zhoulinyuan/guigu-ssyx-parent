package com.atguigu.ssyx.product.service;

import com.atguigu.ssyx.model.product.SkuImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品图片 服务类
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
public interface SkuImageService extends IService<SkuImage> {

    List<SkuImage> getImageListBySkuId(Long id);
}
