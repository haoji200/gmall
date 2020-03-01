package com.atguigu.gmall.gmalluser.service.impl;


import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.gmalluser.mapper.UmsMemberMapper;
import com.atguigu.gmall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UmsMemberServiceImpl
 *
 * @Author: wd
 * @CreateTime: 2020-03-01
 * @Description:
 */
@Service
public class UmsMemberServiceImpl implements UmsMemberService {

    @Autowired
    private UmsMemberMapper umsMemberMapper;
    @Override
    public List<UmsMember> getAllUser() {
        List<UmsMember> umsMemberList = umsMemberMapper.selectAllUser();
        return umsMemberList;
    }

    @Override
    public List<UmsMember> getAllUser2() {
        List<UmsMember> umsMemberList = umsMemberMapper.selectAll();
        return umsMemberList;
    }

    @Override
    public UmsMember getOne() {
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername("windy");
        UmsMember umsMember1 = umsMemberMapper.selectOne(umsMember);
        return umsMember1;
    }


}