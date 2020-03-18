package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.bean.PmsSkuSaleAttrValue;

import java.util.List;

/**
 * SkuService
 *
 * @Author: wd
 * @CreateTime: 2020-03-06
 * @Description:
 */
public interface SkuService {
    void saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo getSkuInfoById(String skuId);

    List<PmsSkuInfo> getSkuSaleAttrValueListBySpu(String productId);

    List<PmsSkuInfo> getAllSkuInfo();
}