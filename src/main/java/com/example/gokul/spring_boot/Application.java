package com.example.gokul.spring_boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

// 1. This is the constructor and field Injection example

//@SpringBootApplication
//public class Application implements CommandLineRunner {
//
////	final NotificationService notificationService;
//
////    public Application(NotificationService notificationService) {
////        this.notificationService = notificationService;
////    }
////
////    public static void main(String[] args) {
////		SpringApplication.run(Application.class, args);
////	}
////
////	@Override
////	public void run(String... args) throws Exception {
////		notificationService.send("Hello bhai");
////	}

// 2. This below code is to get all the beans available in a map and not choose between any of them

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	Map<String, NotificationService> notificationServiceMap = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		 for(var notification: notificationServiceMap.entrySet()){
			 String key = notification.getKey();
			 System.out.println(key);
			 notification.getValue().send("Hello from the map contents");
		 }
	}
}
