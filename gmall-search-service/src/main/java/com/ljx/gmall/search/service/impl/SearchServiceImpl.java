package com.ljx.gmall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.ljx.gmall.bean.PmsSearchParam;
import com.ljx.gmall.bean.PmsSearchSkuInfo;
import com.ljx.gmall.bean.PmsSkuAttrValue;
import com.ljx.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam searchParam) {

        //使用dsl工具封装方法获取dsl语句
        String dslStr = getDslStr(searchParam);
        List<PmsSearchSkuInfo> searchSkuInfoList = new ArrayList<>();
        //Api执行复杂查询
        Search search = new Search.Builder(dslStr).addIndex("gmall0105").addType("PmsSkuInfo").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(search);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = execute.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits) {
            PmsSearchSkuInfo source = hit.source;
            //加高亮
//            Map<String, List<String>> highlight = hit.highlight;
//            String skuName = highlight.get("skuName").get(0);
//            source.setSkuName(skuName);

            searchSkuInfoList.add(source);
        }
        return searchSkuInfoList;
    }

    private String getDslStr(PmsSearchParam searchParam) {
        List<PmsSkuAttrValue> skuAttrValueList = searchParam.getSkuAttrValueList();
        String keyword = searchParam.getKeyword();
        String catalog3Id = searchParam.getCatalog3Id();
        //jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool复杂查询的语句
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter,terms并集,term交集

        if(StringUtils.isNotBlank(catalog3Id)){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id",catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }


        if(skuAttrValueList != null){
            for (PmsSkuAttrValue skuAttrValue : skuAttrValueList) {
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList",skuAttrValue.getValueId());
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        //must:match匹配的文本
        if(StringUtils.isNotBlank(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName",keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        //query
        searchSourceBuilder.query(boolQueryBuilder);
        //highlighter高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        //更改高亮的样式
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        //从那一条数据开始
        searchSourceBuilder.from(0);
        //一共几条
        searchSourceBuilder.size(20);
        //排序
        searchSourceBuilder.sort("id",SortOrder.DESC);

        String dslStr = searchSourceBuilder.toString();
        System.out.println("搜索语句为"+dslStr);
        return dslStr;
    }
}
