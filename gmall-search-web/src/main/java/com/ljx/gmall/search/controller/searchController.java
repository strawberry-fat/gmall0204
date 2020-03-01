package com.ljx.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljx.gmall.bean.*;
import com.ljx.gmall.service.AttrService;
import com.ljx.gmall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class searchController {
    @Reference
    SearchService searchService;
    @Reference
    AttrService attrService;

    @RequestMapping("index")
    public String index() {
        return "index";
    }


    @RequestMapping("list.html")
    public String list(PmsSearchParam searchParam, ModelMap modelMap) {
        //调用搜索服务，返回搜索结果
        List<PmsSearchSkuInfo> searchSkuInfoList = searchService.list(searchParam);
        //抽取检索结果包含的平台属性集合
        Set<String> valueIdSet = new HashSet<>();
        for (PmsSearchSkuInfo searchSkuInfo : searchSkuInfoList) {
            List<PmsSkuAttrValue> skuAttrValueList = searchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue skuAttrValue : skuAttrValueList) {
                String valueId = skuAttrValue.getValueId();
                valueIdSet.add(valueId);
            }
        }

        //在attrService中，根据valueId将属性列表查询出来
        List<PmsBaseAttrInfo> pmsBaseAttrInfoList = attrService.getAttrValueListByValueId(valueIdSet);
        //对结果进一步处理，去掉当前条件中valueId所在的属性组
        //已经被选中的ValueId数组
        String[] delValueIds = searchParam.getValueId();
        if (delValueIds != null) {
            //封装面包屑所需要的的参数
            List<PmsSearchCrumb> searchCrumbs = new ArrayList<>();
            for (String delValueId : delValueIds) {
                //平台属性集合，
                Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfoList.iterator();
                //面包屑参数对象
                PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                //生成面包屑的参数
                pmsSearchCrumb.setValueId(delValueId);
                pmsSearchCrumb.setUrlParam(getUrlParamCrumb(searchParam, delValueId));
                while (iterator.hasNext()) {
                    PmsBaseAttrInfo pmsBaseAttrInfo = iterator.next();
                    List<PmsBaseAttrValue> attrValueList = pmsBaseAttrInfo.getAttrValueList();
                    for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                        String valueId = pmsBaseAttrValue.getId();
                        if (delValueId.equals(valueId)) {
                            //给面包屑的属性名赋值
                            pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                            //删除所在的属性组
                            iterator.remove();
                        }
                    }
                }
                searchCrumbs.add(pmsSearchCrumb);
            }
            modelMap.put("attrValueSelectedList", searchCrumbs);
        }

        //返给页面
        String urlParam = getUrlParam(searchParam);
        modelMap.put("urlParam", urlParam);
        modelMap.put("skuLsInfoList", searchSkuInfoList);
        modelMap.put("attrList", pmsBaseAttrInfoList);
        //关键字不为空返给页面，面包屑
        String keyword = searchParam.getKeyword();
        if (StringUtils.isNotBlank(keyword)) {
            modelMap.put("keyword", keyword);
        }
        return "list";
    }

    private String getUrlParam(PmsSearchParam searchParam) {
        String urlParam = "";
        String catalog3Id = searchParam.getCatalog3Id();
        String keyword = searchParam.getKeyword();
        String[] skuAttrValueList = searchParam.getValueId();
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if (skuAttrValueList != null) {
            for (String valueId : skuAttrValueList) {
                urlParam = urlParam + "&valueId=" + valueId;
            }
        }
        return urlParam;
    }

    private String getUrlParamCrumb(PmsSearchParam searchParam, String delValueId) {
        String urlParam = "";
        String catalog3Id = searchParam.getCatalog3Id();
        String keyword = searchParam.getKeyword();
        String[] skuAttrValueList = searchParam.getValueId();
        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isNotBlank(urlParam)) {
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if (skuAttrValueList != null) {
            for (String valueId : skuAttrValueList) {
                if (!valueId.equals(delValueId)) {
                    urlParam = urlParam + "&valueId=" + valueId;
                }
            }
        }
        return urlParam;
    }
}
