package com.atguigu.gmall.search;



import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.gmall.bean.PmsBaseCatalog1;
import com.atguigu.gmall.bean.PmsSearchSkuInfo;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.CatalogService;
import com.atguigu.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@SpringBootTest
@RunWith(SpringRunner.class)
public class GmallSearchServiceApplicationTests {

    @Autowired
    JestClient jestClient;

    @Reference
    SkuService skuService;

    @Reference
    CatalogService catalogService;

    @Test
    public void contextLoads() throws Exception {

        List<PmsBaseCatalog1> baseCatalog1s = catalogService.getCatalogJson();
        File file = new File("D:/catalogJson.json");
        FileOutputStream fs = new FileOutputStream(file);
        String jsonString = JSON.toJSONString(baseCatalog1s);
        fs.write(jsonString.getBytes());
//        Search search = new Search.Builder("{}").addIndex("gmall0830").addType("pmsSearchSkuInfo").build();
//
//        SearchResult execute = jestClient.execute(search);// 参数必须是es增删改查的某一个对象
//
//        Long total = execute.getTotal();
//
//        System.out.println(total+"====================================");

        // 查询mysql的数据
//        List<PmsSkuInfo> pmsSkuInfoList = skuService.getAllSkuInfo();

        // 转化成PmsSearchSkuInfo
//        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
//        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
//            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
//            BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);
//            pmsSearchSkuInfo.setId(Long.parseLong(pmsSkuInfo.getId()));
//            pmsSearchSkuInfos.add(pmsSearchSkuInfo);
//        }
//        System.out.println(pmsSearchSkuInfos.size());
//        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
//            Index index = new Index.Builder(pmsSearchSkuInfo).index("gmall_1015").type("pmsSerarchSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
//            jestClient.execute(index);
//        }



    }

}
