package com.atguigu.gmall.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ItemController
 *
 * @Author: wd
 * @CreateTime: 2020-03-06
 * @Description:
 */
@Controller
@CrossOrigin
public class ItemController {

    @Reference
    private SkuService skuService;
    @Reference
    private SpuService spuService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "index";
    }

    @RequestMapping("{skuId}.html")
    public String getItem(@PathVariable String skuId, Map map){
        PmsSkuInfo skuInfo = skuService.getSkuInfoById(skuId);
        map.put("skuInfo",skuInfo);
        String productId = skuInfo.getProductId();

//        List<PmsProductSaleAttr> productSaleAttrs = spuService.spuSaleAttrList(productId);
        List<PmsProductSaleAttr> productSaleAttrs = spuService.spuSaleAttrListBySql(productId);
        map.put("spuSaleAttrListCheckBySku",productSaleAttrs);
        return "item";
    }
}