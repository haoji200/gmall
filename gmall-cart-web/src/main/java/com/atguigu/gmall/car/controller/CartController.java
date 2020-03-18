package com.atguigu.gmall.car.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.OmsCartItem;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.service.SkuService;
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

/**
 * CartController
 *
 * @Author: wd
 * @CreateTime: 2020-03-14
 * @Description:
 */
@Controller
public class CartController {

    @Reference
    private SkuService skuService;

    @Reference
    private CartService cartService;

    @RequestMapping("checkCart")
    public String checkCart(OmsCartItem omsCartItem,HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        String userId = "1";
        //用户未登录，走cookie
        if(StringUtils.isBlank(userId)){
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isNotBlank(cartListCookie)){
                omsCartItems = JSON.parseArray(cartListCookie,OmsCartItem.class);
                for (OmsCartItem cartItem : omsCartItems) {
                    if(omsCartItem.getProductSkuId().equals(cartItem.getProductSkuId())){
                        cartItem.setIsChecked(omsCartItem.getIsChecked());
                    }
                }
            }
            CookieUtil.setCookie(request,response,"cartListCookie",JSON.toJSONString(omsCartItems),60*60*24,true);
        }
        //用户已登录，走DB
        else{
            omsCartItem.setMemberId(userId);
            cartService.updateCartItem(omsCartItem);
            omsCartItems = cartService.getCartItemsByUserId(userId);
        }
        modelMap.put("cartList",omsCartItems);

        return "cartListInner";
    }

    private BigDecimal getSumPrice(List<OmsCartItem> omsCartItems) {
        BigDecimal sum = new BigDecimal(0);
        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal price = omsCartItem.getPrice();
            BigDecimal quantity = omsCartItem.getQuantity();
            BigDecimal singleSum = price.multiply(quantity);
            sum = sum.add(singleSum);
        }
        return sum;
    }
    private List<BigDecimal> getSingleSumPrice(List<OmsCartItem> omsCartItems) {
        List<BigDecimal> singleSumList = new ArrayList<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            BigDecimal price = omsCartItem.getPrice();
            BigDecimal quantity = omsCartItem.getQuantity();
            BigDecimal singleSum = price.multiply(quantity);
            singleSumList.add(singleSum);
        }
        return singleSumList;
    }

    @RequestMapping("cartList")
    public String cartList(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        String userId = "1";
        //用户未登录，走cookie
        if(StringUtils.isBlank(userId)){
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            if(StringUtils.isNotBlank(cartListCookie)){
                omsCartItems = JSON.parseArray(cartListCookie,OmsCartItem.class);
            }
        }
        //用户已登录，走DB
        else{
            omsCartItems = cartService.getCartItemsByUserId(userId);
        }
        modelMap.put("cartList",omsCartItems);
        modelMap.put("sumPrice",getSumPrice(omsCartItems));
        modelMap.put("singleSumPrice",getSingleSumPrice(omsCartItems));

        return "cartList";
    }


    @RequestMapping("addToCart")
    public String addToCart(OmsCartItem omsCartItem,HttpServletRequest request, HttpServletResponse response){

        List<OmsCartItem> omsCartItems = new ArrayList<>();
        PmsSkuInfo pmsSkuInfo = skuService.getSkuInfoById(omsCartItem.getProductSkuId());
        omsCartItem.setIsChecked("1");
        omsCartItem.setProductId(pmsSkuInfo.getProductId());
        omsCartItem.setProductPic(pmsSkuInfo.getSkuDefaultImg());
        omsCartItem.setProductCategoryId(pmsSkuInfo.getCatalog3Id());
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setPrice(pmsSkuInfo.getPrice());
        omsCartItem.setProductName(pmsSkuInfo.getSkuName());

        String userId = "1";
        //未登录
        if(StringUtils.isBlank(userId)){
            String cartListCookie = CookieUtil.getCookieValue(request, "cartListCookie", true);
            //浏览器没有cookie
            if(StringUtils.isBlank(cartListCookie)){
                omsCartItems.add(omsCartItem);
            }
            //浏览器有cookie
            else{
                omsCartItems = JSON.parseArray(cartListCookie,OmsCartItem.class);
                boolean b = if_skuId_exsists(omsCartItems,omsCartItem);
                //cookie中已有相同的数据
                if(b){
                    for (OmsCartItem cartItem : omsCartItems) {
                        if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                            cartItem.setQuantity(cartItem.getQuantity().add(omsCartItem.getQuantity()));
                        }
                    }
                }
                //cookie中没有有相同的数据
                else{
                    omsCartItems.add(omsCartItem);
                }
            }
            CookieUtil.setCookie(request,response,"cartListCookie", JSON.toJSONString(omsCartItems),60*60*24,true);
        }
        //已登录
        else{
            omsCartItem.setMemberId(userId);
            OmsCartItem omsCartItemFromDB = cartService.getCartItemExists(omsCartItem);
            //已有该商品
            if(omsCartItemFromDB!=null){
                omsCartItemFromDB.setQuantity(omsCartItemFromDB.getQuantity().add(omsCartItem.getQuantity()));
                cartService.updateCartItem(omsCartItemFromDB);
            }
            //没有该商品
            else{
                cartService.addCartItem(omsCartItem);
            }
            cartService.flushUserCart(userId);

        }
        return "redirect:/success.html";
    }

    private boolean if_skuId_exsists(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {
        boolean b = false;
        for (OmsCartItem cartItem : omsCartItems) {
            if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                b = true;
            }
        }
        return b;
    }
}