package com.nc.edu.phonenet.model;

/**
 * Created by Ксения on 2/7/2016.
 */
public class CallRegister {
    private Subscriber outCaller;
    private Subscriber inCaller;
    private double cost;

    public CallRegister(Subscriber outCaller, Subscriber inCaller,double cost) {
        this.outCaller = outCaller;
        this.inCaller = inCaller;
        this.cost = cost;
    }
}
