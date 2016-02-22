package com.nc.edu.phonenet;

import com.nc.edu.phonenet.model.Subscriber;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/**
 * Created by Ксения on 2/4/2016.
 */
public class Main {
    public static void main(String [] args) {
        String fileName = "SubList.txt";
        String line = "9034593321 Romanov Roman Romanovich 10.0";
       // String line = "9034593257 Semenov Semen Semenovich 10.0";
        String chunks[] = line.split(" ");
        JSONObject ob = new JSONObject();
        ob.put("s_number", chunks[0]);
        ob.put("s_surname", chunks[1]);
        ob.put("s_fname", chunks[2]);
        ob.put("s_sname", chunks[3]);
        ob.put("s_balance", chunks[4]);
        try (FileWriter writer = new FileWriter(fileName, true)){
            ob.writeJSONString(writer);
        } catch (IOException ex) {
        }
    }
}
