package com.example.configfromfilesystem.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @Value("${spring.application.name}")
    String springApplicationName;

    @GetMapping("/test")
    String test(){
        return "test application: " + springApplicationName;
    }
}
