package com.example.gokul.spring_boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	PaymentService paymentService = null;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("Hello there!!!");
	}

	@Override
	public void run(String... args) throws Exception {
		paymentService.pay();
	}
}
