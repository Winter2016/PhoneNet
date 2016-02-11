package com.nc.edu.phonenet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 2/5/2016.
 */
public class SubscrWriter {
    private String fileName;
    public SubscrWriter (String fName)
    {
        fileName = fName;
    }
    public void writeSubscr(String line) throws IOException {
        BufferedWriter writer = null;
        writer = new BufferedWriter(new FileWriter(fileName,true));
        writer.append(line);
        writer.append(System.getProperty("line.separator"));
        writer.close();
    }
}
