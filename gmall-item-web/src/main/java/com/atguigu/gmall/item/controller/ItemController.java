package com.atguigu.gmall.item.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsProductSaleAttr;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;
import com.atguigu.gmall.service.SkuService;
import com.atguigu.gmall.service.SpuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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
        System.out.println("1");
        PmsSkuInfo skuInfo = skuService.getSkuInfoById(skuId);
        System.out.println("2");
        map.put("skuInfo",skuInfo);
        String productId = skuInfo.getProductId();

//        List<PmsProductSaleAttr> productSaleAttrs = spuService.spuSaleAttrList(productId);
        List<PmsProductSaleAttr> productSaleAttrs = spuService.spuSaleAttrListBySql(productId,skuInfo.getId());
        map.put("spuSaleAttrListCheckBySku",productSaleAttrs);

        List<PmsSkuInfo> skuInfoList = skuService.getSkuSaleAttrValueListBySpu(skuInfo.getProductId());
        HashMap<String, String> hashMap = new HashMap<>();
        for (PmsSkuInfo pmsSkuInfo : skuInfoList) {
            List<PmsSkuSaleAttrValue> skuSaleAttrValues = pmsSkuInfo.getSkuSaleAttrValueList();
            String v = pmsSkuInfo.getId();
            String k = "";
            for (PmsSkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValues) {
                String saleAttrValueId = skuSaleAttrValue.getSaleAttrValueId();
                k += "|" + saleAttrValueId;
            }
            hashMap.put(k,v);
        }
        String jsonString = JSON.toJSONString(hashMap);
        map.put("jsonString",jsonString);
        return "item";
    }

    @RequestMapping("{skuId}.html2")
    public String getItem2(@PathVariable String skuId, Map map){
        PmsSkuInfo skuInfo = skuService.getSkuInfoById(skuId);
        map.put("skuInfo",skuInfo);
        String productId = skuInfo.getProductId();

        List<PmsProductSaleAttr> productSaleAttrs = spuService.spuSaleAttrListBySql(productId,skuInfo.getId());
        map.put("spuSaleAttrListCheckBySku",productSaleAttrs);

        map.put("productId",productId);
        return "item";
    }
}