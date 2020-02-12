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
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import java.util.List;
import java.util.UUID;

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

            //设置分布式锁
            String token = UUID.randomUUID().toString();
            String OK = jedis.set("sku:" + skuId + ":lock", token, "nx", "px", 10);
            if(StringUtils.isNoneBlank(OK)&&"OK".equals(OK)){
                //成功获取到锁之后，10s内可以访问数据库。
                skuInfo = getSkuByIdFromDb(skuId);

                if(skuInfo != null){
                    //mysql查询结果存入Redis
                    jedis.set("sku:"+skuId+":info",JSON.toJSONString(skuInfo));
                }else {
                    //如果数据库中不存在，为了防止缓存穿透，将null或者空字符串设置给redis
                    jedis.setex("sku:"+skuId+":info",60*2,JSON.toJSONString(""));
                }

                //释放分布式锁,根据key值判断只能删除自己的锁。
                String keyToken = jedis.get("sku:" + skuId + ":lock");
                if(StringUtils.isNotBlank(keyToken) && keyToken.equals(token)){
                    //jedis.eval(lua);
                    jedis.del("sku:" + skuId + ":lock");
                }
            }else {
                //设置失败，没有获取分布式锁。自旋。
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return getSkuById(skuId);
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
