package com.nc.edu.phonenet.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Ксения on 3/21/2016.
 */
public class DAO {
    public static Connection conn;

    // Connection to database
    public static void connect() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:PhoneNetDB.s3db");
    }

    // Close connection
    public static void closeDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
    }
}
