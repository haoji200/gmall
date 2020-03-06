package com.atguigu.gmall.service;


import com.atguigu.gmall.bean.UmsMember;

import java.util.List;

/**
 * UmsMemberService
 *
 * @Author: wd
 * @CreateTime: 2020-03-01
 * @Description:
 */
public interface UmsMemberService {

    List<UmsMember> getAllUser();


    List<UmsMember> getAllUser2();

    UmsMember getOne();
}
