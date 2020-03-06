package com.atguigu.gmall.user.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * UserController
 *
 * @Author: wd
 * @CreateTime: 2020-03-01
 * @Description:
 */
@Controller
public class UserController {

    @Reference
    private UmsMemberService umsMemberService;

    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "index";
    }

    @RequestMapping("/All")
    @ResponseBody
    public List<UmsMember> getAllUser(){
        List<UmsMember> umsMemberList = umsMemberService.getAllUser();
        return umsMemberList;
    }

    @RequestMapping("/All2")
    @ResponseBody
    public List<UmsMember> getAllUser2(){
        List<UmsMember> umsMemberList = umsMemberService.getAllUser2();
        return umsMemberList;
    }

    @RequestMapping("/getOne")
    @ResponseBody
    public UmsMember getOne(){
        UmsMember umsMember = umsMemberService.getOne();
        return umsMember;
    }
}