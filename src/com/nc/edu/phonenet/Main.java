package com.nc.edu.phonenet;

import com.nc.edu.phonenet.model.Subscriber;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.nc.edu.phonenet.read_write.JSONSubscrReaderWriter;
import com.nc.edu.phonenet.read_write.SubscrReaderWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by Ксения on 2/4/2016.
 */
public class Main {
    public static void main(String [] args) {
        String fileName = "SubscriberList.txt";
        SubscrReaderWriter rw = new SubscrReaderWriter(fileName);
        List<Subscriber> sslist = new ArrayList<>();
        sslist.add(0, new Subscriber("Ivanov", "Ivan", "Ivanovich", "9085674312", Double.valueOf(10.0)));
        sslist.add(1, new Subscriber("Petrov", "Petr", "Petrovich", "9085674313", Double.valueOf(20.0)));
        sslist.add(2, new Subscriber("Karlov", "Karl", "Karlovich", "9085674314", Double.valueOf(30.0)));
        Subscriber s = new Subscriber("Naumov", "Naum", "Naumovich", "9604563467", Double.valueOf(40.0));
        rw.writeSubscr(s);
        rw.rewriteSubscr(sslist);
        List<Subscriber> slist2 = rw.readSubscr();
        for (Subscriber ss:slist2)
          System.out.println(ss.getFullName() + ' ' + ss.getPhnumber() + ' ' + Double.valueOf(ss.getBalance()).toString() );
    }
}
