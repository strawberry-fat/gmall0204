package com.ljx.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljx.gmall.bean.PmsProductImage;
import com.ljx.gmall.bean.PmsSkuInfo;
import com.ljx.gmall.service.SkuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin
@Controller
public class SkuController {
    @Reference
    SkuService skuService;

    @PostMapping("saveSkuInfo")
    @ResponseBody
    public String saveSkuInfo(@RequestBody PmsSkuInfo skuInfo) {
        //将skuId赋值给produceId
        skuInfo.setProductId(skuInfo.getSpuId());
        //处理默认图片
        String skuDefaultImage = skuInfo.getSkuDefaultImg();
        if (StringUtils.isBlank(skuDefaultImage)) {
            skuInfo.setSkuDefaultImg(skuInfo.getSkuImageList().get(0).getImgUrl());
        }

        skuService.saveSkuInfo(skuInfo);
        return "success";
    }
}
