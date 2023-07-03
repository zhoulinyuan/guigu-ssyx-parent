package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.SkuAttrValue;
import com.atguigu.ssyx.model.product.SkuImage;
import com.atguigu.ssyx.model.product.SkuInfo;
import com.atguigu.ssyx.model.product.SkuPoster;
import com.atguigu.ssyx.mq.constant.MqConst;
import com.atguigu.ssyx.mq.service.RabbitService;
import com.atguigu.ssyx.product.mapper.SkuInfoMapper;
import com.atguigu.ssyx.product.service.SkuAttrValueService;
import com.atguigu.ssyx.product.service.SkuImageService;
import com.atguigu.ssyx.product.service.SkuInfoService;
import com.atguigu.ssyx.product.service.SkuPosterService;
import com.atguigu.ssyx.vo.product.SkuInfoQueryVo;
import com.atguigu.ssyx.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * sku信息 服务实现类
 * </p>
 *
 * @author zqf
 * @since 2023-06-25
 */
@Service
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoMapper, SkuInfo> implements SkuInfoService {
    @Autowired//图片
    private SkuImageService skuImageService;
    @Autowired//属性
    private SkuAttrValueService skuAttrValueService;
    @Autowired//海报
    private SkuPosterService skuPosterService;

    @Autowired
    private RabbitService rabbitService;
    @Override
    public IPage<SkuInfo> selectPage(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo) {
        String keyword = skuInfoQueryVo.getKeyword();
        String skuType = skuInfoQueryVo.getSkuType();
        Long categoryId = skuInfoQueryVo.getCategoryId();
        //封装条件
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(keyword)) {
            wrapper.like(SkuInfo::getSkuName,keyword);
        }
        if(!StringUtils.isEmpty(skuType)) {
            wrapper.eq(SkuInfo::getSkuType,skuType);
        }
        if(!StringUtils.isEmpty(categoryId)) {
            wrapper.eq(SkuInfo::getCategoryId,categoryId);
        }
        //调用方法查询
        IPage<SkuInfo> skuInfoPage = baseMapper.selectPage(pageParam, wrapper);
        return skuInfoPage;
    }

    @Override
    public void saveSkuInfo(SkuInfoVo skuInfoVo) {
        //1.保存sku基本信息
        //SkuInfoVo --> SkuInfo
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo,skuInfo);
        baseMapper.insert(skuInfo);
        //2.保存sku图片信息
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if(!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuInfo.getId());
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        //3.保存sku图片
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if(!CollectionUtils.isEmpty(skuImagesList)) {
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuInfo.getId());
            }
            skuImageService.saveBatch(skuImagesList);
        }
        //4.保存sku平台属性
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if(!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }
    }
    @Override
    public SkuInfoVo getSkuInfo(Long id) {
        SkuInfoVo skuInfoVo = new SkuInfoVo();
        SkuInfo skuInfo = baseMapper.selectById(id);
        //根据id查询商品图片列表
        List<SkuImage> skuImageList= skuImageService.getImageListBySkuId(id);
        //根据id查询商品海报列表
        List<SkuPoster> skuPosterList= skuPosterService.getPosterListBySkuId(id);
        //根据id查询商品属性列表
        List<SkuAttrValue> skuAttrValueList= skuAttrValueService.getAttrValueListBySkuId(id);

        BeanUtils.copyProperties(skuInfo,skuInfoVo);
        skuInfoVo.setSkuImagesList(skuImageList);
        skuInfoVo.setSkuPosterList(skuPosterList);
        skuInfoVo.setSkuAttrValueList(skuAttrValueList);
        return skuInfoVo;
    }

    @Override
    public void updateSkuInfo(SkuInfoVo skuInfoVo) {
        //修改基本信息
        SkuInfo skuInfo = new SkuInfo();
        BeanUtils.copyProperties(skuInfoVo,skuInfo);
        baseMapper.updateById(skuInfo);
        Long skuId = skuInfoVo.getId();
        //海报信息
        LambdaQueryWrapper<SkuPoster> skuPosterLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuPosterLambdaQueryWrapper.eq(SkuPoster::getSkuId,skuId);
        skuPosterService.remove(skuPosterLambdaQueryWrapper);
        List<SkuPoster> skuPosterList = skuInfoVo.getSkuPosterList();
        if(!CollectionUtils.isEmpty(skuPosterList)) {
            for (SkuPoster skuPoster : skuPosterList) {
                skuPoster.setSkuId(skuId);
            }
            skuPosterService.saveBatch(skuPosterList);
        }
        //商品图片
        LambdaQueryWrapper<SkuImage> skuImageLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuImageLambdaQueryWrapper.eq(SkuImage::getSkuId,skuId);
        skuImageService.remove(skuImageLambdaQueryWrapper);
        List<SkuImage> skuImagesList = skuInfoVo.getSkuImagesList();
        if(!CollectionUtils.isEmpty(skuImagesList)) {
            for (SkuImage skuImage : skuImagesList) {
                skuImage.setSkuId(skuId);
            }
            skuImageService.saveBatch(skuImagesList);
        }

        //商品属性
        LambdaQueryWrapper<SkuAttrValue> skuAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        skuAttrValueLambdaQueryWrapper.eq(SkuAttrValue::getSkuId,skuId);
        skuAttrValueService.remove(skuAttrValueLambdaQueryWrapper);
        List<SkuAttrValue> skuAttrValueList = skuInfoVo.getSkuAttrValueList();
        if(!CollectionUtils.isEmpty(skuAttrValueList)) {
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuId);
            }
            skuAttrValueService.saveBatch(skuAttrValueList);
        }

    }

    @Override
    public void check(Long id, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(id);
        skuInfo.setCheckStatus(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public void publish(Long id, Integer status) {
        if (status ==1){
            SkuInfo skuInfo = baseMapper.selectById(id);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);
            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_UPPER, id);
        }else {
            SkuInfo skuInfo = baseMapper.selectById(id);
            skuInfo.setPublishStatus(status);
            baseMapper.updateById(skuInfo);

            rabbitService.sendMessage(MqConst.EXCHANGE_GOODS_DIRECT,
                    MqConst.ROUTING_GOODS_LOWER, id);
        }


    }

    @Override
    public void isNewPerson(Long skuId, Integer status) {
        SkuInfo skuInfo = baseMapper.selectById(skuId);
        skuInfo.setId(skuId);
        skuInfo.setIsNewPerson(status);
        baseMapper.updateById(skuInfo);
    }

    @Override
    public List<SkuInfo> findSkuInfoByKeyword(String keyword) {
        List<SkuInfo> skuInfoList = baseMapper.selectList(new LambdaQueryWrapper<SkuInfo>().like(SkuInfo::getSkuName, keyword));
        return skuInfoList;
    }

    @Override
    public List<SkuInfo> findSkuInfoList(List<Long> skuIdList) {

        return baseMapper.selectBatchIds(skuIdList);
    }

    @Override
    public List<SkuInfo> findNewPersonSkuInfoList() {
        Page<SkuInfo> page = new Page<>(1, 3);
        LambdaQueryWrapper<SkuInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuInfo::getIsNewPerson,1);
        wrapper.eq(SkuInfo::getPublishStatus,1);
        wrapper.orderByDesc(SkuInfo::getStock);

        Page<SkuInfo> skuInfoPage = baseMapper.selectPage(page, wrapper);
        List<SkuInfo> records = skuInfoPage.getRecords();
        return records;
    }
}
