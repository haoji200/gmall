package com.atguigu.gmall.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.*;
import com.atguigu.gmall.service.AttrService;
import com.atguigu.gmall.service.SearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * SearchController
 *
 * @Author: wd
 * @CreateTime: 2020-03-13
 * @Description:
 */
@Controller
@CrossOrigin
public class SearchController {

    @Reference
    private SearchService searchService;

    @Reference
    private AttrService attrService;

    @RequestMapping("list.html")
    public String search(PmsSearchParam pmsSearchParam,ModelMap modelMap){

        List<PmsSearchSkuInfo> pmsSearchSkuInfos = searchService.getInfos(pmsSearchParam);
        modelMap.put("skuLsInfoList",pmsSearchSkuInfos);

        Set<String> valueIdSet = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                String valueId = pmsSkuAttrValue.getValueId();
                valueIdSet.add(valueId);
            }
        }
        String urlParam = getAllUrlParam(pmsSearchParam);
        modelMap.put("urlParam",urlParam);
        if(valueIdSet!=null&&valueIdSet.size()>0){
            List<PmsBaseAttrInfo> pmsBaseAttrInfos = attrService.getArrtInfoListByValueIds(valueIdSet);
            modelMap.put("attrList",pmsBaseAttrInfos);
            if(pmsSearchParam.getValueId()!=null&&pmsSearchParam.getValueId().length>0){
                List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();
                String[] deleteIds = pmsSearchParam.getValueId();
                for (String deleteId : deleteIds) {
                    PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
                    String crumbUrlParam = getAllUrlParam(pmsSearchParam,deleteId);
                    pmsSearchCrumb.setUrlParam(crumbUrlParam);
                    pmsSearchCrumbs.add(pmsSearchCrumb);
                    Iterator<PmsBaseAttrInfo> iterator = pmsBaseAttrInfos.iterator();
                    while(iterator.hasNext()){
                        PmsBaseAttrInfo next = iterator.next();
                        List<PmsBaseAttrValue> attrValueList = next.getAttrValueList();
                        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
                            String attrValueId = pmsBaseAttrValue.getId();
                            if(deleteId.equals(attrValueId)){
                                pmsSearchCrumb.setValueName(pmsBaseAttrValue.getValueName());
                                iterator.remove();
                            }
                        }
                    }
                }
//                for (String valueId : pmsSearchParam.getValueId()) {
//                    PmsSearchCrumb pmsSearchCrumb = new PmsSearchCrumb();
//                    pmsSearchCrumb.setValueId(valueId);
//                    pmsSearchCrumb.setValueName(valueId);
//                    String crumbUrlParam = getAllUrlParam(pmsSearchParam,valueId);
//                    pmsSearchCrumb.setUrlParam(crumbUrlParam);
//                    pmsSearchCrumbs.add(pmsSearchCrumb);
//                }
                modelMap.put("attrValueSelectedList",pmsSearchCrumbs);
            }
        }


        return "list";
    }
    public String getAllUrlParam(PmsSearchParam pmsSearchParam, String... crumbvalueId){
        String catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        String[] valueIds = pmsSearchParam.getValueId();
        String urlParam = "";
        if(StringUtils.isNotBlank(catalog3Id)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "catalog3Id=" + catalog3Id;
        }
        if(StringUtils.isNotBlank(keyword)){
            if(StringUtils.isNotBlank(urlParam)){
                urlParam = urlParam + "&";
            }
            urlParam = urlParam + "keyword=" + keyword;
        }
        if(valueIds!=null&&valueIds.length>0){
            for (String valueId : valueIds) {
                if(crumbvalueId!=null&&crumbvalueId.length>0){
                    if(!crumbvalueId[0].equals(valueId)){
                        urlParam = urlParam + "&" + "valueId=" + valueId;
                    }
                }else{
                    urlParam = urlParam + "&" + "valueId=" + valueId;
                }

            }
        }
        return urlParam;
    }
//    public String getCrumbUrlParam(PmsSearchParam pmsSearchParam, String CrumbvalueId){
//        String catalog3Id = pmsSearchParam.getCatalog3Id();
//        String keyword = pmsSearchParam.getKeyword();
//        String[] valueIds = pmsSearchParam.getValueId();
//        String urlParam = "";
//        if(StringUtils.isNotBlank(catalog3Id)){
//            if(StringUtils.isNotBlank(urlParam)){
//                urlParam = urlParam + "&";
//            }
//            urlParam = urlParam + "catalog3Id=" + catalog3Id;
//        }
//        if(StringUtils.isNotBlank(keyword)){
//            if(StringUtils.isNotBlank(urlParam)){
//                urlParam = urlParam + "&";
//            }
//            urlParam = urlParam + "keyword=" + keyword;
//        }
//        if(valueIds!=null&&valueIds.length>0){
//            for (String valueId : valueIds) {
//                if(!CrumbvalueId.equals(valueId)){
//                    urlParam = urlParam + "&" + "valueId=" + valueId;
//                }
//            }
//        }
//        return urlParam;
//    }
//    public String getUrlParam(PmsSearchParam pmsSearchParam){
//        String catalog3Id = pmsSearchParam.getCatalog3Id();
//        String keyword = pmsSearchParam.getKeyword();
//        String[] valueIds = pmsSearchParam.getValueId();
//        String urlParam = "";
//        if(StringUtils.isNotBlank(catalog3Id)){
//            if(StringUtils.isNotBlank(urlParam)){
//                urlParam = urlParam + "&";
//            }
//            urlParam = urlParam + "catalog3Id=" + catalog3Id;
//        }
//        if(StringUtils.isNotBlank(keyword)){
//            if(StringUtils.isNotBlank(urlParam)){
//                urlParam = urlParam + "&";
//            }
//            urlParam = urlParam + "keyword=" + keyword;
//        }
//        if(valueIds!=null&&valueIds.length>0){
//            for (String valueId : valueIds) {
//                urlParam = urlParam + "&" + "valueId=" + valueId;
//            }
//        }
//        return urlParam;
//    }

    @RequestMapping("test")
    public String test(){
        return "index";
    }
}