package com.ljx.gmall.service;

import com.ljx.gmall.bean.OmsCartItem;

import java.util.List;

public interface CartService {
    //根据用户Id和商品Id，获取购物车中的数据，如果没有返回null
    OmsCartItem isExistCart(String memberId, String skuId);
    //向购物车中增加数据
    void addCart(OmsCartItem omsCartItem);
    //更新购物车数据
    void updateCart(OmsCartItem omsCartItemFromDb);
    //更新redis中的数据
    void flushCartCache(String memberId);
    //已登录用户查询购物车
    List<OmsCartItem> cartList(String userId);
    //更新购物车中的选中状态
    void checkCart(OmsCartItem omsCartItem);

}
