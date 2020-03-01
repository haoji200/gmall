package com.atguigu.gmall.gmalluser.mapper;


import com.atguigu.gmall.bean.UmsMember;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * UmsMemberMapper
 *
 * @Author: wd
 * @CreateTime: 2020-03-01
 * @Description:
 */
public interface UmsMemberMapper extends Mapper<UmsMember> {
    List<UmsMember> selectAllUser();

}