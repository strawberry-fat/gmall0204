package com.ljx.gmall.item.controlle;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ljx.gmall.bean.PmsProductSaleAttr;
import com.ljx.gmall.bean.PmsSkuInfo;
import com.ljx.gmall.bean.PmsSkuSaleAttrValue;
import com.ljx.gmall.service.SkuService;
import com.ljx.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //查询当前sku相关的sku信息的hash表
        Map<String,String> skuSaleValueHash = new HashMap<>();
        List<PmsSkuInfo> skuInfos =  skuService.getSkuSaleAttrValueListBySpu(skuInfo.getProductId());
        for (PmsSkuInfo pmsSkuInfo : skuInfos) {
            String k = "";
            String v = pmsSkuInfo.getId();
            List<PmsSkuSaleAttrValue> skuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
            for (PmsSkuSaleAttrValue saleAttrValue : skuSaleAttrValues) {
                k+=saleAttrValue.getSaleAttrValueId()+"|";
            }
            skuSaleValueHash.put(k,v);
        }
        String jsonString = JSON.toJSONString(skuSaleValueHash);
        map.put("skuSaleValueHash",jsonString);
        return "item";
    }
}
