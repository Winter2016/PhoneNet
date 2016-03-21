package com.nc.edu.phonenet.DAO;

import com.nc.edu.phonenet.model.Subscriber;

import javax.swing.*;
import java.beans.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 3/21/2016.
 */
public class SubscriberDAO extends DAO{
    public static java.sql.Statement statmt;
    public static ResultSet resSet;

    // Subscriber table creation
    public static void createTableSubscriber() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'Subscriber' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'surname' text, 'fname' text, 'sname' text, 'phnumber' text, 'balance' real);");
    }
    //Filling table
    public static void writeTableSubscriber(Subscriber sc) throws SQLException
    {
        String surname = "'" + sc.getSurName() + "'";
        String fname = "'" + sc.getFirstName() + "'";
        String sname = "'" + sc.getSecondName() + "'";
        String phnumber ="'" + sc.getPhnumber() + "'";
        Double balance = sc.getBalance();
        String statforexe = "INSERT INTO 'Subscriber' ('surname', 'fname', 'sname', 'phnumber', 'balance') VALUES  (" + surname + ", " + fname + ", " + sname + ", " + phnumber + ", " + balance + ");";
        statmt.execute(statforexe);
    }

    public  static void writeTableSubscriber(List<Subscriber> sslist) throws SQLException
    {
        for(Subscriber ss : sslist)
        {
            writeTableSubscriber(ss);
        }
    }

    // Table output
    public static List<Subscriber> readTableSubscriber() throws ClassNotFoundException, SQLException
    {
        List<Subscriber> sslist = new ArrayList<>();
        resSet = statmt.executeQuery("SELECT * FROM Subscriber;");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  surname = resSet.getString("surname");
            String  fname = resSet.getString("fname");
            String  sname = resSet.getString("sname");
            String  phnumber = resSet.getString("phnumber");
            Double balance = resSet.getDouble("balance");
            sslist.add(new Subscriber(surname, fname, sname, phnumber, balance));
        }
        return sslist;
    }

    //Find id by phone number
    public  static int findIDByPhnumber(String phnumber) throws ClassNotFoundException, SQLException {
        resSet = statmt.executeQuery("SELECT id FROM Subscriber WHERE phnumber = " + "'" + phnumber + "';");
        return resSet.getInt("id");
    }

    //Find Subscriber by id
    public static Subscriber findSubscrByID(int id) throws ClassNotFoundException, SQLException {
        String statforexe = "SELECT * FROM Subscriber WHERE id = " + "'" + id + "';";
        resSet = statmt.executeQuery(statforexe);
        String  surname = resSet.getString("surname");
        String  fname = resSet.getString("fname");
        String  sname = resSet.getString("sname");
        String  phnumber = resSet.getString("phnumber");
        Double balance = resSet.getDouble("balance");
        Subscriber ss = new Subscriber(surname, fname, sname, phnumber, balance);
        return ss;
    }

    public void findSubscr (String str, DefaultListModel listModel) throws SQLException {
        if (str.isEmpty())
            listModel.addElement("Пожалуйста, введите запрос");
        else {
            String statforexe = "SELECT * FROM Subscriber WHERE (surname like '%" + str + "%') or (fname like '%" + str + "%') or (sname like '%" + str + "%') or (phnumber like '%" + str + "%');";
            resSet = statmt.executeQuery(statforexe);
            while(resSet.next())
            {
                String  surname = resSet.getString("surname");
                String  fname = resSet.getString("fname");
                String  sname = resSet.getString("sname");
                String  phnumber = resSet.getString("phnumber");
                Double balance = resSet.getDouble("balance");
                listModel.addElement(phnumber + ' ' + surname + ' ' + fname + ' ' + sname + ' ' + balance);
            }
            if (listModel.isEmpty())
                listModel.addElement("Совпадений не найдено");
        }
    }

    public void changeBalance(String phNumber, Double cost) throws SQLException {
        String statforexe = "SELECT id, balance FROM Subscriber WHERE phnumber = '" + phNumber + "';";
        resSet = statmt.executeQuery(statforexe);
        int id = resSet.getInt("id");
        double balance = resSet.getDouble("balance");
        balance = balance - cost;
        String statforexe2 = "UPDATE Subscriber SET balance = " + balance + " WHERE id = " + id + ";";
        statmt.execute(statforexe2);
    }

    public void changeBalance (Subscriber ss, Double cost) throws SQLException {
        changeBalance(ss.getPhnumber(), cost);
    }

    //Delete string from table
    public void deleteFromSubscriber(Subscriber ss) throws SQLException {
        String statforexe = "DELETE FROM Subscriber WHERE phnumber = '" + ss.getPhnumber() + "';";
        resSet = statmt.executeQuery(statforexe);
    }

    public void deleteFromSubscriberByID (int id) throws SQLException {
        String statforexe = "DELETE FROM Subscriber WHERE id = '" +id + "';";
        statmt.execute(statforexe);
    }

    // Close connection
    public static void closeDB() throws ClassNotFoundException, SQLException
    {
        resSet.close();
    }
}
