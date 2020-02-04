package com.ljx.gmall.manage.controller;


import Utill.PmsUploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.ljx.gmall.bean.PmsBaseAttrInfo;
import com.ljx.gmall.bean.PmsBaseAttrValue;
import com.ljx.gmall.bean.PmsBaseSaleAttr;
import com.ljx.gmall.bean.PmsProductInfo;
import com.ljx.gmall.service.AttrService;
import com.ljx.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin
@Controller
public class AttrController {
    @Reference
    AttrService attrService;
    @Reference
    SpuService spuService;

    //查询平台属性
    @GetMapping("attrInfoList")
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(String catalog3Id) {
        List<PmsBaseAttrInfo> attrInfos = attrService.attrInfoList(catalog3Id);
        return attrInfos;
    }

    //保存平台属性
    @PostMapping("saveAttrInfo")
    @ResponseBody
    public String saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo) {
        String success = attrService.saveAttrInfo(pmsBaseAttrInfo);
        return success;
    }

    //    getAttrValueList
    //根据平台属性ID查询，属性值
    @PostMapping("getAttrValueList")
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(String attrId) {
        List<PmsBaseAttrValue> pmsBaseAttrValueList = attrService.getAttrValueList(attrId);
        return pmsBaseAttrValueList;
    }

    //枚举销售属性，颜色，尺寸等。
    @PostMapping("baseSaleAttrList")
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        List<PmsBaseSaleAttr> pmsBaseSaleAttrList = attrService.baseSaleAttrList();
        return pmsBaseSaleAttrList;
    }

    //销售属性的保存
    @PostMapping("saveSpuInfo")
    @ResponseBody
    public String saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo) {
        spuService.saveSpuInfo(pmsProductInfo);
        return "secuss";
    }

    //图片上传
    @PostMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile) {
        //将图片存储到服务器上
        //返回图片地址
        String imgUrl = PmsUploadUtil.uploadImage(multipartFile);
        System.out.println(imgUrl);
        return imgUrl;
    }
}
