package com.example.gokul.spring_boot;

import org.springframework.stereotype.Component;

@Component
public class PaymentService {

    public void pay(){
        System.out.println("Hello from Payment service!!!");
    }
}
