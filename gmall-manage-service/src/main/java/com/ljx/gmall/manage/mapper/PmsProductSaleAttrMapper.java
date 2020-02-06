package com.ljx.gmall.manage.mapper;

import com.ljx.gmall.bean.PmsProductSaleAttr;
import com.ljx.gmall.manage.BaseMapper.TkBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsProductSaleAttrMapper extends TkBaseMapper<PmsProductSaleAttr> {
    List<PmsProductSaleAttr> getspuSaleAttrListCheckBySku(@Param("productId") String productId,@Param("skuId") String skuId);
}
