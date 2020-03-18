package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsSearchParam;
import com.atguigu.gmall.bean.PmsSearchSkuInfo;

import java.util.List;

/**
 * SearchService
 *
 * @Author: wd
 * @CreateTime: 2020-03-13
 * @Description:
 */
public interface SearchService {
    List<PmsSearchSkuInfo> getInfos(PmsSearchParam pmsSearchParam);
}