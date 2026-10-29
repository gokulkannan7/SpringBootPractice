package com.example.gokul.spring_boot.impl;

import com.example.gokul.spring_boot.NotificationService;
import org.springframework.stereotype.Component;

@Component
public class SmsNotification implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println(message + " Hello from SMS Service!!");
    }
}
