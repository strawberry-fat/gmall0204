package com.ljx.gmall.manage.mapper;

import com.ljx.gmall.bean.PmsSkuInfo;
import com.ljx.gmall.manage.BaseMapper.TkBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmsSkuInfoMapper extends TkBaseMapper<PmsSkuInfo> {
    List<PmsSkuInfo> selectSkuSaleAttrValueListBySpu(@Param("productId") String productId);
}
