package com.ljx.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ljx.gmall.bean.PmsSkuAttrValue;
import com.ljx.gmall.bean.PmsSkuImage;
import com.ljx.gmall.bean.PmsSkuInfo;
import com.ljx.gmall.bean.PmsSkuSaleAttrValue;
import com.ljx.gmall.manage.mapper.PmsSkuAttrValueMapper;
import com.ljx.gmall.manage.mapper.PmsSkuImageMapper;
import com.ljx.gmall.manage.mapper.PmsSkuInfoMapper;
import com.ljx.gmall.manage.mapper.PmsSkuSaleAttrValueMapper;
import com.ljx.gmall.service.SkuService;
import com.ljx.gmall.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    PmsSkuInfoMapper pmsSkuInfoMapper;

    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;

    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;

    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public void saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        // 插入skuInfo
        int i = pmsSkuInfoMapper.insertSelective(pmsSkuInfo);
        String skuId = pmsSkuInfo.getId();

        // 插入平台属性关联
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueMapper.insertSelective(pmsSkuAttrValue);
        }

        // 插入销售属性关联
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueMapper.insertSelective(pmsSkuSaleAttrValue);
        }

        // 插入图片信息
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageMapper.insertSelective(pmsSkuImage);
        }


    }
    public PmsSkuInfo getSkuByIdFromDb(String skuId){
        //查询skuInfo对象信息
        PmsSkuInfo pmsSkuInfo = new PmsSkuInfo();
        pmsSkuInfo.setId(skuId);
        PmsSkuInfo skuInfo = pmsSkuInfoMapper.selectOne(pmsSkuInfo);
        //获取图片列表
        PmsSkuImage pmsSkuImage = new PmsSkuImage();
        pmsSkuImage.setSkuId(skuId);
        List<PmsSkuImage> imageList = pmsSkuImageMapper.select(pmsSkuImage);
        skuInfo.setSkuImageList(imageList);
        return skuInfo;
    }


    @Override
    public PmsSkuInfo getSkuById(String skuId) {
        //查询skuInfo对象信息
        PmsSkuInfo skuInfo = new PmsSkuInfo();
        //链接缓存
        Jedis jedis = redisUtil.getJedis();
        //查询缓存
        String skuKey = "sku:"+skuId+":info";
        String skuJson = jedis.get(skuKey);

        if(StringUtils.isNotBlank(skuJson)){
            skuInfo  = JSON.parseObject(skuJson, PmsSkuInfo.class);
        }else {
            //如果缓存中没有查询数据库
            skuInfo = getSkuByIdFromDb(skuId);
            //mysql查询结果存入Redis
            if(skuInfo != null){
                jedis.set("sku:"+skuId+":info",JSON.toJSONString(skuInfo));
            }else {
                //如果数据库中不存在，为了防止缓存穿透，将null或者空字符串设置给redis
                jedis.setex("sku:"+skuId+":info",60*2,JSON.toJSONString(""));
            }
        }
        jedis.close();
//        skuInfo = getSkuByIdFromDb(skuId);
        return skuInfo;
    }

    @Override
    public List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId) {
        List<PmsSkuInfo> skuInfoList = pmsSkuInfoMapper.selectSkuSaleAttrValueListBySpu(productId);
        return skuInfoList;
    }
}
