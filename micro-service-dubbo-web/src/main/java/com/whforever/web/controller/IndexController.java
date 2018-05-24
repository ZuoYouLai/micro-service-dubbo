package com.whforever.web.controller;

import com.alibaba.fastjson.JSON;
import com.whforever.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/")
public class IndexController {

    @Autowired
    DemoService demoService;

    @RequestMapping("/echo")
    @ResponseBody
    public String echo() {
        System.out.println(">>>>>>echo");
        return JSON.toJSONString(demoService.getUsers());
    }
}
