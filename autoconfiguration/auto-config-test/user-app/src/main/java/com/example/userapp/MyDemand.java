package com.example.userapp;

import com.demand.svc.DemandBean;

public class MyDemand implements DemandBean {
    @Override
    public void show() {
        System.out.println("This is from mydemand......");
    }
}
