package com.nc.edu.phonenet.DAO;

import com.nc.edu.phonenet.model.Subscriber;

import javax.swing.*;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 3/21/2016.
 */
public class SubscriberDAO extends DAO{
    public static Statement statmt;
    public static ResultSet resSet;
    public static PreparedStatement prepStat;

    // Subscriber table creation
    public static void createTableSubscriber() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'Subscriber' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'surname' text, 'fname' text, 'sname' text, 'phnumber' text, 'balance' real);");
    }
    //Filling table
    public static void writeTableSubscriber(Subscriber sc) throws SQLException
    {
        String surname = sc.getSurName();
        String fname = sc.getFirstName();
        String sname = sc.getSecondName();
        String phnumber = sc.getPhnumber();
        Double balance = sc.getBalance();
        String statforexe = "INSERT INTO 'Subscriber' ('surname', 'fname', 'sname', 'phnumber', 'balance') VALUES  (?,?,?,?,?);";
        prepStat = conn.prepareStatement(statforexe);
        prepStat.setString(Integer.valueOf(1),surname);
        prepStat.setString(Integer.valueOf(2), fname);
        prepStat.setString(Integer.valueOf(3), sname);
        prepStat.setString(Integer.valueOf(4), phnumber);
        prepStat.setDouble(Integer.valueOf(5), balance);
        prepStat.executeUpdate();
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
        String statforexe = "SELECT id FROM Subscriber WHERE phnumber = ?;";
        prepStat = conn.prepareStatement(statforexe);
        prepStat.setString(Integer.valueOf(1),phnumber);
        resSet = prepStat.executeQuery();
        return resSet.getInt("id");
    }

    //Find Subscriber by id
    public static Subscriber findSubscrByID(int id) throws ClassNotFoundException, SQLException {
        String statforexe = "SELECT * FROM Subscriber WHERE id = ?;";
        prepStat = conn.prepareStatement(statforexe);
        prepStat.setInt(Integer.valueOf(1),id);
        resSet = prepStat.executeQuery();
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
            String statforexe = "SELECT * FROM Subscriber WHERE (surname like ?) or (fname like ?) or (sname like ?) or (phnumber like ?);";
            prepStat = conn.prepareStatement(statforexe);
            prepStat.setString(Integer.valueOf(1),"%" + str + "%");
            prepStat.setString(Integer.valueOf(2),"%" + str + "%");
            prepStat.setString(Integer.valueOf(3),"%" + str + "%");
            prepStat.setString(Integer.valueOf(4),"%" + str + "%");
            resSet = prepStat.executeQuery();
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
        String statforexe = "SELECT id, balance FROM Subscriber WHERE phnumber = ?;";
        prepStat = conn.prepareStatement(statforexe);
        prepStat.setString(Integer.valueOf(1),phNumber);
        resSet = prepStat.executeQuery();
        int id = resSet.getInt("id");
        double balance = resSet.getDouble("balance");
        balance = balance - cost;
        String statforexe2 = "UPDATE Subscriber SET balance = ? WHERE id = ?;";
        prepStat = conn.prepareStatement(statforexe2);
        prepStat.setDouble(Integer.valueOf(1),balance);
        prepStat.setInt(Integer.valueOf(2),id);
        prepStat.executeUpdate();
    }

    public void changeBalance (Subscriber ss, Double cost) throws SQLException {
        changeBalance(ss.getPhnumber(), cost);
    }

    //Delete string from table
    public void deleteFromSubscriber(Subscriber ss) throws SQLException {
        String statforexe = "DELETE FROM Subscriber WHERE phnumber = ?;";
        prepStat = conn.prepareStatement(statforexe);
        prepStat.setString(Integer.valueOf(1),ss.getPhnumber());
        prepStat.executeUpdate();
    }

    public void deleteFromSubscriberByID (int id) throws SQLException {
        String statforexe = "DELETE FROM Subscriber WHERE id = ?;";
        prepStat = conn.prepareStatement(statforexe);
        prepStat.setInt(Integer.valueOf(1),id);
        prepStat.executeUpdate();
    }

    // Close connection
    public static void closeDB() throws ClassNotFoundException, SQLException
    {
            resSet.close();
    }
}
