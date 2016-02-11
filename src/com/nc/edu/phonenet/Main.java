package com.nc.edu.phonenet;

import com.nc.edu.phonenet.model.Subscriber;

import java.io.IOException;
import java.util.List;

/**
 * Created by Ксения on 2/4/2016.
 */
public class Main {
    public static void main(String [] args) throws IOException {
        SubscrWriter sw = new SubscrWriter("SubscriberList.txt");
        try {
            sw.writeSubscr("9064563234 Иванов Иван Иванович 0.0");
            sw.writeSubscr("9064562356 Петров Петр Петрович 50.0");
        } catch (IOException e) {
            e.printStackTrace();
        }

        SubscrReader sr = new SubscrReader("SubscriberList.txt");
        List<Subscriber> ss =  sr.readSubscr();

        ss.get(0).replenish(10.0);
        SubscrReWriter srw = new SubscrReWriter("SubscriberList.txt");
        srw.rewriteSubscr(ss);
      //  sw.writeSubscr(ss.get(0).replenish(10.0));
    }
}
