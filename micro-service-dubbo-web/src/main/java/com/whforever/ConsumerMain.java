package com.whforever;

import com.whforever.model.User;
import com.whforever.service.provider.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ConsumerMain {
    public static void main(String[] args) {
        //Prevent to get IPV6 address,this way only work in debug mode
        //But you can pass use -Djava.net.preferIPv4Stack=true,then it work well whether in debug mode or not
        System.setProperty("java.net.preferIPv4Stack", "true");
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"dubbo-consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService"); // get remote service proxy


        while (true) {
            try {
                Thread.sleep(1000);

                String hello = demoService.sayHello("world"); // call remote method
                System.out.println(hello); // get result

                List<User> userList = demoService.getUsers();
                for (User user : userList) {
                    System.out.println(user.toString());
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
