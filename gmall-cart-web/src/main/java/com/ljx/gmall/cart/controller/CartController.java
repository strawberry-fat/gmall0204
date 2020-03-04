package com.ljx.gmall.cart.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ljx.gmall.bean.OmsCartItem;
import com.ljx.gmall.bean.PmsSkuInfo;
import com.ljx.gmall.service.CartService;
import com.ljx.gmall.service.SkuService;
import com.ljx.gmall.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CartController {

    @Reference
    SkuService skuService;
    @Reference
    CartService cartService;

    //添加商品到购物车
    @RequestMapping("addToCart")
    public String addToCart(String skuId, int quantity, HttpServletRequest request, HttpServletResponse response){
        List<OmsCartItem> omsCartItemList = new ArrayList<>();
        //调用商品服务，查询商品信息
        PmsSkuInfo skuInfo = skuService.getSkuById(skuId);
        //将商品信息封装成购物车信息
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setModifyDate(new Date());
        omsCartItem.setPrice(skuInfo.getPrice());
        omsCartItem.setProductAttr("");
        omsCartItem.setProductBrand("");
        omsCartItem.setProductCategoryId(skuInfo.getCatalog3Id());
        omsCartItem.setProductId(skuInfo.getProductId());
        omsCartItem.setProductName(skuInfo.getSkuName());
        omsCartItem.setProductPic(skuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("11111111111");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(new BigDecimal(quantity));
        //判断用户是否登录
        String memberId = "1";
        if(StringUtils.isBlank(memberId)){
            //未登录
            //从cookie中取出cartList
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isBlank(cartListCookie)){
                //cookie中没数据
                omsCartItemList.add(omsCartItem);
            }else {
                //cookie中有数据，赋值给集合
                omsCartItemList = JSON.parseArray(cartListCookie,OmsCartItem.class);
                //判读添加的购物车数据是否已经存在
                boolean exist = ifCartExist(omsCartItemList,omsCartItem);
                if(exist){
                    //已存在，就更新数量
                    for (OmsCartItem cartItem : omsCartItemList) {
                        if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                            cartItem.setQuantity(cartItem.getQuantity().add(omsCartItem.getQuantity()));
                        }
                    }
                }else {
                    //不存在，就添加。 
                    omsCartItemList.add(omsCartItem);
                }
            }
            CookieUtil.setCookie(request,response,"cartListCookie", JSON.toJSONString(omsCartItemList),60*60*72,true);
        }else{
            //用户已登录
            //从db中查出购物车数据，如果返回值为空，说明这个商品没有添加过，如果不为空，说明这个商品曾经添加过
            OmsCartItem omsCartItemFromDb = cartService.isExistCart(memberId,skuId);
            if(omsCartItemFromDb == null){
                //说明购物车里没有这个商品
                omsCartItem.setMemberId(memberId);
                cartService.addCart(omsCartItem);
            }else {
                //说明购物车里存在这个商品
                omsCartItemFromDb.setQuantity(omsCartItem.getQuantity().add(omsCartItemFromDb.getQuantity()));
                cartService.updateCart(omsCartItemFromDb);
            }
            //同步缓存
            cartService.flushCartCache(memberId);
        }
        return "redirect:/success.html";
    }

    private boolean ifCartExist(List<OmsCartItem> omsCartItemList, OmsCartItem omsCartItem) {
        boolean exist = false;
        for (OmsCartItem cartItem : omsCartItemList) {
            String productSkuId = cartItem.getProductSkuId();
            if(productSkuId.equals(omsCartItem.getProductSkuId())){
                exist = true;
            }
        }
        return exist;
    }

    //列出购物车
    @RequestMapping("cartList")
    public String cartList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        List<OmsCartItem> omsCartItemList = new ArrayList<>();
        String memberId = "1";
        if(StringUtils.isNotBlank(memberId)){
            //已经登录查询db
            omsCartItemList = cartService.cartList(memberId);
        }else {
            //没有登录查询cookie
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isNotBlank(cartListCookie)){
                omsCartItemList = JSON.parseArray(cartListCookie,OmsCartItem.class);
            }
        }
        for (OmsCartItem omsCartItem : omsCartItemList) {
            omsCartItem.setTotalPrice(omsCartItem.getQuantity().multiply(omsCartItem.getPrice()));
        }
        modelMap.put("cartList",omsCartItemList);
        //被勾选商品的总价
        BigDecimal totalAmount = getTotalAmount(omsCartItemList);
        modelMap.put("totalAmount",totalAmount);
        return "cartList";
    }
    //获取总价的方法
    private BigDecimal getTotalAmount(List<OmsCartItem> omsCartItemList) {
        BigDecimal totalAmount = new BigDecimal(0);
        for (OmsCartItem omsCartItem : omsCartItemList) {
            BigDecimal totalPrice = omsCartItem.getTotalPrice();
            if(omsCartItem.getIsChecked().equals("1")){
                totalAmount =  totalAmount.add(totalPrice);
            }
        }
        return totalAmount;
    }

    //异步购物车状态改变
    @RequestMapping("checkCart")
    public String checkCart(String isChecked,String skuId,HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        String memberId = "1";
        //调用服务，修改状态
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(memberId);
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setIsChecked(isChecked);
        cartService.checkCart(omsCartItem);
        //将最新的购物车数据，渲染给页面
        List<OmsCartItem> omsCartItemList = cartService.cartList(memberId);
        //被勾选商品的总价
        BigDecimal totalAmount = getTotalAmount(omsCartItemList);
        modelMap.put("totalAmount",totalAmount);
        modelMap.put("cartList",omsCartItemList);
        return "cartListInner";
    }
}
