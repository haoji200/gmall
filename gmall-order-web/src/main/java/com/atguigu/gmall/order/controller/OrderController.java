package com.atguigu.gmall.order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * OrderController
 *
 * @Author: wd
 * @CreateTime: 2020-03-18
 * @Description:
 */
@Controller
public class OrderController {

    @RequestMapping("toTrade")
    public String toTrade(){
        return "trade";
    }
    @RequestMapping("list")
    public String list(){
        return "list";
    }
    @RequestMapping("tradeFail")
    public String tradeFail(){
        return "tradeFail";
    }
}