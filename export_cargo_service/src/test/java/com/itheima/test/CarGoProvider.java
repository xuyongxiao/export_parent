package com.itheima.test;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class CarGoProvider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext-*.xml");
        ac.start();
        System.in.read();
    }

}
