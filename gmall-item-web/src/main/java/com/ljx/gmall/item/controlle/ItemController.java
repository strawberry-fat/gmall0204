package com.ljx.gmall.item.controlle;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljx.gmall.bean.PmsProductSaleAttr;
import com.ljx.gmall.bean.PmsSkuInfo;
import com.ljx.gmall.service.SkuService;
import com.ljx.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
public class ItemController {
    @Reference
    SkuService skuService;
    @Reference
    SpuService spuService;

    @RequestMapping("index")
    public String index(ModelMap modelMap) {
        modelMap.put("hello", "hello thymeleaf!!");
        return "index";
    }

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable String skuId, ModelMap map) {
        //返回skuInfo对象
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId);
        map.put("skuInfo", skuInfo);
        //返回销售属性列表
        List<PmsProductSaleAttr> saleAttrList = spuService.spuSaleAttrListCheckBySku(skuInfo.getProductId(),skuInfo.getId());
        map.put("spuSaleAttrListCheckBySku",saleAttrList);
        return "item";
    }
}
