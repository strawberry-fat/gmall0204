package com.ljx.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ljx.gmall.bean.PmsSearchParam;
import com.ljx.gmall.bean.PmsSearchSkuInfo;
import com.ljx.gmall.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
public class searchController {
    @Reference
    SearchService searchService;

    @RequestMapping("index")
    public String index(){
        return "index";
    }


    @RequestMapping("list.html")
    public String list(PmsSearchParam searchParam, ModelMap modelMap){
        //调用搜索服务，返回搜索结果
        List<PmsSearchSkuInfo> searchSkuInfoList = searchService.list(searchParam);

        //返给页面
        modelMap.put("skuLsInfoList",searchSkuInfoList);
        return "list";
    }
}
