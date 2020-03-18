package com.atguigu.gmall.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsBaseCatalog2;
import com.atguigu.gmall.bean.PmsBaseCatalog3;
import com.atguigu.gmall.manage.mapper.CatalogMapper;
import com.atguigu.gmall.manage.mapper.PmsBaseCatalog2Mapper;
import com.atguigu.gmall.manage.mapper.PmsBaseCatalog3Mapper;
import com.atguigu.gmall.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * CatalogServiceImpl
 *
 * @Author: wd
 * @CreateTime: 2020-03-03
 * @Description:
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CatalogMapper catalogMapper;
    @Autowired
    private PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Autowired
    private PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;

    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        List<PmsBaseCatalog1> pmsBaseCatalog1List = catalogMapper.selectAll();
        return pmsBaseCatalog1List;
    }

    @Override
    public List<PmsBaseCatalog2> getCatalog2(String catalog1Id) {
        PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
        pmsBaseCatalog2.setCatalog1Id(catalog1Id);
        List<PmsBaseCatalog2> pmsBaseCatalog2List = pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
        return pmsBaseCatalog2List;
    }

    @Override
    public List<PmsBaseCatalog3> getCatalog3(String catalog2Id) {
        PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
        pmsBaseCatalog3.setCatalog2Id(catalog2Id);
        List<PmsBaseCatalog3> pmsBaseCatalog3List = pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
        return pmsBaseCatalog3List;
    }

    @Override
    public List<PmsBaseCatalog1> getCatalogJson() {
        List<PmsBaseCatalog1> pmsBaseCatalog1List = catalogMapper.selectAll();
        for (PmsBaseCatalog1 pmsBaseCatalog1 : pmsBaseCatalog1List) {
            String catalog1Id = pmsBaseCatalog1.getId();
            PmsBaseCatalog2 pmsBaseCatalog2 = new PmsBaseCatalog2();
            pmsBaseCatalog2.setCatalog1Id(catalog1Id);
            List<PmsBaseCatalog2> pmsBaseCatalog2s = pmsBaseCatalog2Mapper.select(pmsBaseCatalog2);
            pmsBaseCatalog1.setCatalog2s(pmsBaseCatalog2s);
            for (PmsBaseCatalog2 baseCatalog2 : pmsBaseCatalog2s) {
                String catalog2Id = baseCatalog2.getId();
                PmsBaseCatalog3 pmsBaseCatalog3 = new PmsBaseCatalog3();
                pmsBaseCatalog3.setCatalog2Id(catalog2Id);
                List<PmsBaseCatalog3> baseCatalog3s = pmsBaseCatalog3Mapper.select(pmsBaseCatalog3);
                baseCatalog2.setCatalog3List(baseCatalog3s);
            }
        }
        return pmsBaseCatalog1List;
    }

}