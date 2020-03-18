package com.atguigu.gmall.cart.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.gmall.bean.OmsCartItem;
import com.atguigu.gmall.cart.mapper.CartServiceMapper;
import com.atguigu.gmall.service.CartService;
import com.atguigu.gmall.util.RedisUtil ;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CartServiceImpl
 *
 * @Author: wd
 * @CreateTime: 2020-03-15
 * @Description:
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartServiceMapper cartServiceMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public OmsCartItem getCartItemExists(OmsCartItem omsCartItem) {
        OmsCartItem omsCartItemForSelect = new OmsCartItem();
        omsCartItemForSelect.setMemberId(omsCartItem.getMemberId());
        omsCartItemForSelect.setProductSkuId(omsCartItem.getProductSkuId());
        OmsCartItem omsCartItemFromDB = cartServiceMapper.selectOne(omsCartItemForSelect);
        return omsCartItemFromDB;
    }

    @Override
    public void updateCartItem(OmsCartItem omsCartItemFromDB) {
        Example example = new Example(OmsCartItem.class);
        example.createCriteria().andEqualTo("memberId", omsCartItemFromDB.getMemberId()).andEqualTo("productSkuId", omsCartItemFromDB.getProductSkuId());
        OmsCartItem omsCartItemForUpdate = new OmsCartItem();
        if(omsCartItemFromDB.getQuantity()!=null){
            omsCartItemForUpdate.setQuantity(omsCartItemFromDB.getQuantity());
        }
        if(StringUtils.isNotBlank(omsCartItemFromDB.getIsChecked())){
            omsCartItemForUpdate.setIsChecked(omsCartItemFromDB.getIsChecked());
        }
        cartServiceMapper.updateByExampleSelective(omsCartItemForUpdate,example);
        flushUserCart(omsCartItemFromDB.getMemberId());
    }

    @Override
    public void addCartItem(OmsCartItem omsCartItem) {
        cartServiceMapper.insertSelective(omsCartItem);
        Jedis jedis = redisUtil.getJedis();
        jedis.hset("UserId:"+omsCartItem.getMemberId()+"cart",omsCartItem.getProductSkuId(), JSON.toJSONString(omsCartItem));
        jedis.close();
    }

    @Override
    public void flushUserCart(String userId) {
        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setMemberId(userId);
        List<OmsCartItem> omsCartItems = cartServiceMapper.select(omsCartItem);
        if(omsCartItems!=null&&omsCartItems.size()>0){
            Map<String,String> map = new HashMap<>();
            for (OmsCartItem cartItem : omsCartItems) {
                BigDecimal price = cartItem.getPrice();
                BigDecimal quantity = cartItem.getQuantity();
                BigDecimal singleSum = price.multiply(quantity);
                cartItem.setTotalPrice(singleSum);
                map.put(cartItem.getProductSkuId(),JSON.toJSONString(cartItem));
            }
            Jedis jedis = redisUtil.getJedis();
            jedis.hmset("UserId:"+userId+"cart",map);
            jedis.close();
        }
    }

    @Override
    public List<OmsCartItem> getCartItemsByUserId(String userId) {
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        Jedis jedis = redisUtil.getJedis();
        List<String> hvals = jedis.hvals("UserId:" + userId + "cart");
        if(hvals!=null&&hvals.size()>0){
            for (String hval : hvals) {
                OmsCartItem omsCartItem = JSON.parseObject(hval, OmsCartItem.class);
                omsCartItems.add(omsCartItem);
            }
        }else{
            OmsCartItem omsCartItem = new OmsCartItem();
            omsCartItem.setMemberId(userId);
            omsCartItems = cartServiceMapper.select(omsCartItem);
            if(omsCartItems!=null&&omsCartItems.size()>0){
                flushUserCart(userId);
            }
        }
        jedis.close();
        return omsCartItems;
    }


}