package com.whforever.service.impl;

import com.alibaba.dubbo.rpc.RpcContext;
import com.whforever.service.DemoService;
import com.whforever.model.User;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("demoService")
public class DemoServiceImpl implements DemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext
                .getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public List getUsers() {
        List list = new ArrayList();
        User u1 = new User();
        u1.setName("hejingyuan");
        u1.setAge(20);
        u1.setSex("f");

        User u2 = new User();
        u2.setName("xvshu");
        u2.setAge(21);
        u2.setSex("m");


        list.add(u1);
        list.add(u2);

        return list;
    }
}
