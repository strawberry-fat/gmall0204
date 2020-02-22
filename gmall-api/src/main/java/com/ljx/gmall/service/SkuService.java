package com.ljx.gmall.service;

import com.ljx.gmall.bean.PmsSkuInfo;

import java.util.List;

public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuById(String skuId);
    /*
        查询当前sku相关的sku信息
     */
    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);
    List<PmsSkuInfo> getAllSku(String catalog3Id);
}
