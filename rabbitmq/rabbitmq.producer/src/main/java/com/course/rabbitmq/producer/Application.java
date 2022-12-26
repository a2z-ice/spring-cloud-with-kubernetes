package com.course.rabbitmq.producer;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.course.rabbitmq.producer.entity.Employee;
import com.course.rabbitmq.producer.producer.RetryEmployeeProducer;

@SpringBootApplication
//@EnableScheduling
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private RetryEmployeeProducer producer;

	@Override
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			var employee = new Employee("emp" + i, "", LocalDate.of(1990, 12, i + 1));
			producer.sendMessage(employee);
		}
	}
}
