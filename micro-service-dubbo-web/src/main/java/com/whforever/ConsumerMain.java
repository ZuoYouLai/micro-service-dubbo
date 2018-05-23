package com.whforever;

import com.whforever.service.DemoService;
import com.whforever.service.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ConsumerMain {
    public static void main(String[] args) {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{
                "dubbo/dubbo-consumer.xml" });
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy

        List<User> userList = demoService.getUsers();
        for (User user : userList) {
            System.out.println(user.toString());
        }

        while (true) {
            try {
                Thread.sleep(1000);

                String hello = demoService.sayHello("world"); // call remote method
                System.out.println(hello); // get result
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
