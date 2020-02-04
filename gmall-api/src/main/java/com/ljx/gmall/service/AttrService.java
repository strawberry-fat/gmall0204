package com.ljx.gmall.service;

import com.ljx.gmall.bean.PmsBaseAttrInfo;
import com.ljx.gmall.bean.PmsBaseAttrValue;
import com.ljx.gmall.bean.PmsBaseSaleAttr;

import java.util.List;

public interface AttrService {
    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrValue> getAttrValueList(String attrId);

    List<PmsBaseSaleAttr> baseSaleAttrList();
}
