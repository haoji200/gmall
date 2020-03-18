package com.atguigu.gmall.manage.mapper;

import com.atguigu.gmall.bean.PmsBaseAttrInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * PmsBaseAttrInfoMapper
 *
 * @Author: wd
 * @CreateTime: 2020-03-03
 * @Description:
 */
public interface PmsBaseAttrInfoMapper extends Mapper<PmsBaseAttrInfo> {
    List<PmsBaseAttrInfo> selectArrtInfoListByValueIds(@Param("join") String join);
}