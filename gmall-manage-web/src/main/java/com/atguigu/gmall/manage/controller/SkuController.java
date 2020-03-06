package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.SkuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * SkuController
 *
 * @Author: wd
 * @CreateTime: 2020-03-06
 * @Description:
 */
@RestController
@CrossOrigin
public class SkuController {

    @Reference
    private SkuService skuService;

    @RequestMapping("saveSkuInfo")
    public String saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo) {
        skuService.saveSkuInfo(pmsSkuInfo);
        return "success";
    }
}