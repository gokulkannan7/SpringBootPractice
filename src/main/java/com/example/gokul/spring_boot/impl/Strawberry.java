package com.example.gokul.spring_boot.impl;

import com.example.gokul.spring_boot.Frosting;
import com.example.gokul.spring_boot.Syrup;
import org.springframework.stereotype.Component;

@Component
public class Strawberry implements Frosting, Syrup {
    @Override
    public String getFrostingTyoe() {
        return "Inside Strawberry class - From Frosting Interface";
    }

    @Override
    public String getSryupType() {
        return "Inside Strawberry class - From Syrup Interface";
    }
}
