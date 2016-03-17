package com.nc.edu.phonenet.model;

/**
 * Created by Ксения on 2/4/2016.
 */
public class Subscriber implements Replenishable {
    private String surName;
    private String firstName;
    private String secondName;
    private String phnumber;
    private double balance;
    public Subscriber(String surnam, String fnam, String snam,  String phnum, double bal)
    {
        surName = surnam;
        firstName = fnam;
        secondName = snam;
        phnumber = phnum;
        balance = bal;
    }
    public void replenish(double sum)
    {
        balance -= sum;
    }

    public String getFullName() {
        return surName + ' ' + firstName + ' ' + secondName;
    }

    public String getSurName() {
        return surName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getPhnumber() {
        return phnumber;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return this.getPhnumber() + ' ' + this.getFullName() + ' ' + Double.valueOf(this.getBalance()).toString();
    }

    public boolean equalsSubscr(Subscriber sc) {
        return (this.getFullName().equals(sc.getFullName())&&this.getPhnumber().equals(sc.getPhnumber())&&(this.getBalance()==sc.getBalance()));
    }
}
