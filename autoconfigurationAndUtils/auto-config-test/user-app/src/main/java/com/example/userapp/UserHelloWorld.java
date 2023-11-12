package com.example.userapp;

import com.hello.hellospringboot.service.HelloWorld;

public class UserHelloWorld implements HelloWorld {
    @Override
    public void show() {
        System.out.println("From user Hello world.....");
    }
}
