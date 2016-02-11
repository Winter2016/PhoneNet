package com.nc.edu.phonenet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 2/5/2016.
 */
public class SubscrReWriter  {
    private String fileName;
    public SubscrReWriter (String fName)
    {
        fileName = fName;
    }
    public void rewriteSubscr(List<Subscriber> sslist) throws IOException {
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(fileName));
        for (int i = 0; i < sslist.size(); i++) {
            writer.write(sslist.get(i).getPhnumber() + ' ' + sslist.get(i).getName() + ' ' + Double.valueOf(sslist.get(i).getBalance()).toString());
            writer.write(System.getProperty("line.separator"));
        }
        writer.close();
    }
}
