package com.nc.edu.phonenet;

/**
 * Created by Ксения on 2/4/2016.
 */
public class Subscriber implements Replenishable {
    private String name;
    private String phnumber;
    private double balance;
    public Subscriber(String nam, String phnum, double bal)
    {
        name = nam;
        phnumber = phnum;
        balance = bal;
    }
    public void replenish(double sum)
    {
        balance += sum;
    }

    public String getName() {
        return name;
    }

    public String getPhnumber() {
        return phnumber;
    }

    public double getBalance() {
        return balance;
    }

}
