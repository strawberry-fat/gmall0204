package com.ljx.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ljx.gmall.bean.PmsProductImage;
import com.ljx.gmall.bean.PmsProductInfo;
import com.ljx.gmall.bean.PmsProductSaleAttr;
import com.ljx.gmall.bean.PmsProductSaleAttrValue;
import com.ljx.gmall.manage.mapper.PmsProductImageMapper;
import com.ljx.gmall.manage.mapper.PmsProductInfoMapper;
import com.ljx.gmall.manage.mapper.PmsProductSaleAttrMapper;
import com.ljx.gmall.manage.mapper.PmsProductSaleAttrValueMapper;
import com.ljx.gmall.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class SpuServiceImpl implements SpuService {
    @Autowired(required = true)
    PmsProductInfoMapper pmsProductInfoMapper;
    @Autowired(required = true)
    PmsProductImageMapper pmsProductImageMapper;
    @Autowired(required = true)
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired(required = true)
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;

    @Override
    public List<PmsProductInfo> spuList(String catalog3Id) {
        PmsProductInfo pmsProductInfo = new PmsProductInfo();
        pmsProductInfo.setCatalog3Id(catalog3Id);
        List<PmsProductInfo> pmsProductInfos = pmsProductInfoMapper.select(pmsProductInfo);
        System.out.println(pmsProductInfos);
        return pmsProductInfos;
    }

    @Override
    public void saveSpuInfo(PmsProductInfo pmsProductInfo) {
        // 保存商品信息
        pmsProductInfoMapper.insertSelective(pmsProductInfo);

        // 生成商品主键
        String productId = pmsProductInfo.getId();

        // 保存商品图片信息
        List<PmsProductImage> spuImageList = pmsProductInfo.getSpuImageList();
        for (PmsProductImage pmsProductImage : spuImageList) {
            pmsProductImage.setProductId(productId);
            pmsProductImageMapper.insertSelective(pmsProductImage);
        }

        // 保存销售属性信息
        List<PmsProductSaleAttr> spuSaleAttrList = pmsProductInfo.getSpuSaleAttrList();
        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrList) {
            pmsProductSaleAttr.setProductId(productId);
            pmsProductSaleAttrMapper.insertSelective(pmsProductSaleAttr);

            // 保存销售属性值
            List<PmsProductSaleAttrValue> spuSaleAttrValueList = pmsProductSaleAttr.getSpuSaleAttrValueList();
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : spuSaleAttrValueList) {
                pmsProductSaleAttrValue.setProductId(productId);
                pmsProductSaleAttrValueMapper.insertSelective(pmsProductSaleAttrValue);
            }
        }

    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(String spuId) {
        PmsProductSaleAttr productSaleAttr = new PmsProductSaleAttr();
        productSaleAttr.setProductId(spuId);
        List<PmsProductSaleAttr> productSaleAttrs = pmsProductSaleAttrMapper.select(productSaleAttr);
        for (PmsProductSaleAttr pmsProductSaleAttr : productSaleAttrs) {
            PmsProductSaleAttrValue attrValue = new PmsProductSaleAttrValue();
            attrValue.setProductId(spuId);
            attrValue.setSaleAttrId(pmsProductSaleAttr.getSaleAttrId());
            List<PmsProductSaleAttrValue> productSaleAttrValues = pmsProductSaleAttrValueMapper.select(attrValue);
            pmsProductSaleAttr.setSpuSaleAttrValueList(productSaleAttrValues);
        }
        return productSaleAttrs;
    }

    @Override
    public List<PmsProductImage> spuImageList(String spuId) {
        PmsProductImage image = new PmsProductImage();
        image.setProductId(spuId);
        List<PmsProductImage> imageList = pmsProductImageMapper.select(image);
        return imageList;
    }
}
