package com.atguigu.gmall.passport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * PassportController
 *
 * @Author: wd
 * @CreateTime: 2020-03-18
 * @Description:
 */
@Controller
public class PassportController {

    @RequestMapping("index")
    public String index(){
        return "index";
    }
}