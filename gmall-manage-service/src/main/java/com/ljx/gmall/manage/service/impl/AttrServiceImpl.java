package com.ljx.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ljx.gmall.bean.PmsBaseAttrInfo;
import com.ljx.gmall.bean.PmsBaseAttrValue;
import com.ljx.gmall.bean.PmsBaseSaleAttr;
import com.ljx.gmall.manage.mapper.PmsBaseAttrInfoMapper;
import com.ljx.gmall.manage.mapper.PmsBaseAttrValueMapper;
import com.ljx.gmall.manage.mapper.PmsBaseSaleAttrMapper;
import com.ljx.gmall.service.AttrService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {
    @Autowired(required = true)
    PmsBaseAttrInfoMapper attrInfoMapper;
    @Autowired(required = true)
    PmsBaseAttrValueMapper attrValueMapper;
    @Autowired(required = true)
    PmsBaseSaleAttrMapper baseSaleAttrMapper;

    //查询平台属性
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        PmsBaseAttrInfo attrInfo = new PmsBaseAttrInfo();
        attrInfo.setCatalog3Id(catalog3Id);
        List<PmsBaseAttrInfo> attrInfos = attrInfoMapper.select(attrInfo);
        for (PmsBaseAttrInfo baseAttrInfo : attrInfos) {
            List<PmsBaseAttrValue> pmsBaseAttrValues = new ArrayList<>();
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(baseAttrInfo.getId());
            pmsBaseAttrValues = attrValueMapper.select(pmsBaseAttrValue);
            baseAttrInfo.setAttrValueList(pmsBaseAttrValues);
        }
        return attrInfos;
    }

    //保存或者修改平台属性
    @Override
    public String saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        String id = pmsBaseAttrInfo.getId();
        if (StringUtils.isBlank(id)) {
            //ID为空说明保存操作
            //保存属性名，并获取属性主键，当做外键给属性值
            //两种插入方法，insert将所有值都插入数据库，insertSelective只将不为null的值插入数据库
            attrInfoMapper.insertSelective(pmsBaseAttrInfo);

            //保存属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
                attrValueMapper.insertSelective(pmsBaseAttrValue);
            }
        } else {
            //id不为空说明修改操作
            Example e = new Example(PmsBaseAttrInfo.class);
            e.createCriteria().andEqualTo("id", pmsBaseAttrInfo.getId());
            //第二个参数为条件，第一个为值
            attrInfoMapper.updateByExampleSelective(pmsBaseAttrInfo, e);

            //属性值修改
            //根据AttrId删除所有属性值
            PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
            pmsBaseAttrValue.setAttrId(pmsBaseAttrInfo.getId());
            attrValueMapper.delete(pmsBaseAttrValue);
            //重新插入属性值
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
            for (PmsBaseAttrValue baseAttrValue : attrValueList) {
                attrValueMapper.insertSelective(baseAttrValue);
            }
        }
        return "success";
    }

    //销售属性值
    @Override
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        pmsBaseAttrValue.setAttrId(attrId);
        List<PmsBaseAttrValue> attrValueList = attrValueMapper.select(pmsBaseAttrValue);
        return attrValueList;
    }

    //销售属性
    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }
}
