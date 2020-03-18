package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;

import java.util.List;
import java.util.Set;

/**
 * AttrService
 *
 * @Author: wd
 * @CreateTime: 2020-03-03
 * @Description:
 */
public interface AttrService {

    List<PmsBaseAttrInfo> attrInfoList(String catalog3Id);

    void saveAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrInfo> getArrtInfoListByValueIds(Set<String> valueIdSet);
}