package com.ljx.gmall.service;

import com.ljx.gmall.bean.PmsSearchParam;
import com.ljx.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> list(PmsSearchParam searchParam);
}
