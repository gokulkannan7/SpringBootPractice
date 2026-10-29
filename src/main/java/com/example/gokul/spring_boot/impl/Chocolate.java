package com.example.gokul.spring_boot.impl;

import com.example.gokul.spring_boot.Frosting;
import com.example.gokul.spring_boot.Syrup;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class Chocolate implements Frosting, Syrup {
    @Override
    public String getFrostingTyoe() {
        return "Inside Chocolate class - From Frosting Interface";
    }

    @Override
    public String getSryupType() {
        return "Inside Chocolate class - From Syrup Interface";
    }
}
