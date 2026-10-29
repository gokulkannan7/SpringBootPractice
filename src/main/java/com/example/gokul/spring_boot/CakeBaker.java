package com.example.gokul.spring_boot;

import com.example.gokul.spring_boot.impl.Chocolate;
import com.example.gokul.spring_boot.impl.Strawberry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CakeBaker {

    private final Frosting frosting;
    private final Syrup syrup;


    public void bakeCake() {
        System.out.println(frosting.getFrostingTyoe());
        System.out.println(syrup.getSryupType());

    }

    public CakeBaker(Frosting frosting, Syrup syrup) {
        this.frosting = frosting;
        this.syrup = syrup;
    }
}
