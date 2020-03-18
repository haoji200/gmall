package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.OmsCartItem;

import java.util.List;

/**
 * CartService
 *
 * @Author: wd
 * @CreateTime: 2020-03-15
 * @Description:
 */
public interface CartService {
    OmsCartItem getCartItemExists(OmsCartItem omsCartItem);


    void updateCartItem(OmsCartItem omsCartItemFromDB);

    void addCartItem(OmsCartItem omsCartItem);

    void flushUserCart(String userId);

    List<OmsCartItem> getCartItemsByUserId(String userId);
}