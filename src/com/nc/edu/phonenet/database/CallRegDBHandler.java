package com.nc.edu.phonenet.database;

import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 3/20/2016.
 */
public class CallRegDBHandler {
    public static Statement statmt;
    public static ResultSet resSet;

    // Table creation
    public static void CreateTable(Connection conn) throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'CallRegister' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'id_out' INTEGER FOREIGH KEY REFERENCES Subscriber(id), 'id_in' INTEGER FOREIGH KEY REFERENCES Subscriber(id), 'cost' real);");
    }

    //Filling table
    public static void WriteTable(CallRegister callreg) throws SQLException, ClassNotFoundException {
        SubscrDBHandler subscrDBHandler = new SubscrDBHandler();
        Integer id_out = subscrDBHandler.FindID(callreg.getOutCaller().getPhnumber());
        Integer id_in = subscrDBHandler.FindID(callreg.getInCaller().getPhnumber());
        String statforexe = "INSERT INTO 'CallRegister' ('id_out', 'id_in', 'cost') VALUES  (" + id_out + ", " + id_in + ", " + callreg.getCost() + ");";
        statmt.execute(statforexe);
    }

    public  static void WriteTable(List<CallRegister> crlist) throws SQLException, ClassNotFoundException {
        for(CallRegister cr : crlist)
        {
            WriteTable(cr);
        }
    }

    // Table output
    public static List<CallRegister> ReadTable() throws ClassNotFoundException, SQLException
    {
        List<CallRegister> crlist = new ArrayList<>();
        resSet = statmt.executeQuery("SELECT * FROM CallRegister");
        SubscrDBHandler subscrDBHandler = new SubscrDBHandler();
        while(resSet.next())
        {
            int id = resSet.getInt("id");
            int id_out = resSet.getInt("id_out");
            int id_in = resSet.getInt("id_in");
            Double cost = resSet.getDouble("cost");
            Subscriber outScr = subscrDBHandler.FindSubscr(id_out);
            Subscriber inScr = subscrDBHandler.FindSubscr(id_in);
            crlist.add(new CallRegister(outScr, inScr, cost));
        }
        return crlist;
    }

    // Close connection
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        resSet.close();
    }
}
