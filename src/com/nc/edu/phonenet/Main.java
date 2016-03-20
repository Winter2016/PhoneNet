package com.nc.edu.phonenet;

import com.nc.edu.phonenet.database.CallRegDBHandler;
import com.nc.edu.phonenet.database.SubscrDBHandler;
import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;

import java.io.*;
import java.sql.SQLException;
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
  public static void main(String [] args) throws SQLException, ClassNotFoundException {
    //SubscrForm sf = new SubscrForm();
      SubscrDBHandler subscrDBHandler = new SubscrDBHandler();
      subscrDBHandler.Conn();
      subscrDBHandler.CreateTable();
      Subscriber ss = subscrDBHandler.FindSubscr(2);
      System.out.println(ss);
  }
}
