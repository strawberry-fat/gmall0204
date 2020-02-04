package com.ljx.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljx.gmall.bean.PmsBaseCatalog1;
import com.ljx.gmall.bean.PmsBaseCatalog2;
import com.ljx.gmall.bean.PmsBaseCatalog3;
import com.ljx.gmall.service.CatalogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class CatalogController {
    @Reference
    CatalogService catalogService;

    //获取一级分类
    @PostMapping("getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> catalog1s = catalogService.getCatalog1();
        return catalog1s;
    }

    //获取一级分类
    @PostMapping("getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        List<PmsBaseCatalog2> catalog2s = catalogService.getCatalog2(catalog1Id);
        return catalog2s;
    }

    //获取一级分类
    @PostMapping("getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        List<PmsBaseCatalog3> catalog2s = catalogService.getCatalog3(catalog2Id);
        return catalog2s;
    }

}
