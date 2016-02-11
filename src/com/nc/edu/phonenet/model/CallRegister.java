package com.nc.edu.phonenet.model;

/**
 * Created by Ксения on 2/7/2016.
 */
public class CallRegister {
    private Subscriber outCaller;
    private Subscriber inCaller;
    private int duration;
    private double price;

    public CallRegister(Subscriber outCaller, Subscriber inCaller, int duration, double price) {
        this.outCaller = outCaller;
        this.inCaller = inCaller;
        this.duration = duration;
        this.price = price;
    }
    public double costCalc(int duration, double price)
    {
        return duration*price;
    }
}
