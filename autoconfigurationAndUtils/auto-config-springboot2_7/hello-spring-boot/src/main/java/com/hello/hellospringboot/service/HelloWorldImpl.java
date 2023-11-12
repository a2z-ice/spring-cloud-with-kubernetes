package com.hello.hellospringboot.service;

public class HelloWorldImpl implements HelloWorld{
    @Override
    public void show() {
        System.out.println("Hello from autoconfig");
    }
}
