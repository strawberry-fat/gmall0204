package com.ljx.gmall.service;

import com.ljx.gmall.bean.PmsProductImage;
import com.ljx.gmall.bean.PmsProductInfo;
import com.ljx.gmall.bean.PmsProductSaleAttr;

import java.util.List;

public interface SpuService {
    List<PmsProductInfo> spuList(String catalog3Id);

    void saveSpuInfo(PmsProductInfo pmsProductInfo);

    List<PmsProductSaleAttr> spuSaleAttrList(String spuId);

    List<PmsProductImage> spuImageList(String spuId);
}
