package com.example.gokul.spring_boot.impl;

import com.example.gokul.spring_boot.NotificationService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class EmailNotification implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println(message + " Hello from Email Service!!");
    }


}
