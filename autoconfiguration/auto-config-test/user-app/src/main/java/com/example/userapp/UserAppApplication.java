package com.example.userapp;

import com.demand.svc.DemandBean;
import com.hello.hellospringboot.service.HelloWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(UserAppApplication.class, args);
	}





	@Autowired
	private HelloWorld helloWorld;

	@Autowired
	private DemandBean demandBean;

	@Override
	public void run(String... args) throws Exception {
		helloWorld.show();
		demandBean.show();
	}
}
