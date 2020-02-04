package com.ljx.gmall.manage.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.ljx.gmall.bean.PmsBaseCatalog1;
import com.ljx.gmall.bean.PmsBaseCatalog2;
import com.ljx.gmall.bean.PmsBaseCatalog3;
import com.ljx.gmall.manage.mapper.PmsBaseCatalog1Mapper;
import com.ljx.gmall.manage.mapper.PmsBaseCatalog2Mapper;
import com.ljx.gmall.manage.mapper.PmsBaseCatalog3Mapper;
import com.ljx.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {
    //一级分类
    @Autowired(required = true)
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    //二级分类
    @Autowired(required = true)
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    //三级分类
    @Autowired(required = true)
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    //获取一级分类
    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectAll();
    }

    //获取二级分类
    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 catalog2 = new PmsBaseCatalog2();
        catalog2.setCatalog1Id(catalog1Id);
        List<PmsBaseCatalog2> catalog2s = pmsBaseCatalog2Mapper.select(catalog2);
        return catalog2s;
    }

    //获取三级分类
    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 catalog3 = new PmsBaseCatalog3();
        catalog3.setCatalog2Id(catalog2Id);
        List<PmsBaseCatalog3> catalog3s = pmsBaseCatalog3Mapper.select(catalog3);
        return catalog3s;
    }
}
