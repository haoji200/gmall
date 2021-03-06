package com.atguigu.gmall.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;
import com.atguigu.gmall.service.CatalogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * CatalogController
 *
 * @Author: wd
 * @CreateTime: 2020-03-03
 * @Description:
 */
@RestController
@CrossOrigin
public class CatalogController {

    @Reference
    private CatalogService catalogService;


    @RequestMapping("/getCatalog1")
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> pmsBaseCatalog1List = catalogService.getCatalog1();
        return pmsBaseCatalog1List;
    }

    @RequestMapping("/getCatalog2")
    public List<PmsBaseCatalog2> getCatalog2(@RequestParam String catalog1Id) {
        List<PmsBaseCatalog2> pmsBaseCatalog2List = catalogService.getCatalog2(catalog1Id);
        return pmsBaseCatalog2List;
    }

    @RequestMapping("/getCatalog3")
    public List<PmsBaseCatalog3> getCatalog3(@RequestParam String catalog2Id) {
        List<PmsBaseCatalog3> pmsBaseCatalog3List = catalogService.getCatalog3(catalog2Id);
        return pmsBaseCatalog3List;
    }


}