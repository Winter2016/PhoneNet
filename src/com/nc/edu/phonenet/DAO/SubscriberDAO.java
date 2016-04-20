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

    // Subscriber table creation
    public static void createTableSubscriber() throws ClassNotFoundException, SQLException {
        try (Statement statmt = conn.createStatement()) {
            statmt.execute("CREATE TABLE if not exists 'Subscriber' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'surname' text, 'fname' text, 'sname' text, 'phnumber' text, 'balance' real, 'isFree' boolean);");
        }
    }
    //Filling table
    public void writeTableSubscriber(Subscriber sc) throws SQLException
    {
        String surname = sc.getSurName();
        String fname = sc.getFirstName();
        String sname = sc.getSecondName();
        String phnumber = sc.getPhnumber();
        Double balance = sc.getBalance();
        String statforexe = "INSERT INTO 'Subscriber' ('surname', 'fname', 'sname', 'phnumber', 'balance', 'isFree') VALUES  (?,?,?,?,?, 'true');";
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe)) {
            prepStat.setString(1, surname);
            prepStat.setString(2, fname);
            prepStat.setString(3, sname);
            prepStat.setString(4, phnumber);
            prepStat.setDouble(5, balance);
            prepStat.executeUpdate();
        }
    }

    public void writeTableSubscriber(List<Subscriber> sslist) throws SQLException
    {
        for(Subscriber ss : sslist)
        {
            writeTableSubscriber(ss);
        }
    }

    // Table output
    public List<Subscriber> readTableSubscriber() throws ClassNotFoundException, SQLException
    {
        List<Subscriber> sslist = new ArrayList<>();
        try(Statement statmt = conn.createStatement();
        ResultSet resSet = statmt.executeQuery("SELECT * FROM Subscriber;")) {
            while (resSet.next()) {
                String surname = resSet.getString("surname");
                String fname = resSet.getString("fname");
                String sname = resSet.getString("sname");
                String phnumber = resSet.getString("phnumber");
                Double balance = resSet.getDouble("balance");
                sslist.add(new Subscriber(surname, fname, sname, phnumber, balance));
            }
        }
        return sslist;
    }
    //Find Subscriber by ID
    public  static Subscriber findSubscrByID(int id) throws ClassNotFoundException, SQLException {
        String statforexe = "SELECT * FROM Subscriber WHERE id = ?;";
        Subscriber ss;
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe); ResultSet resSet = prepStat.executeQuery();){
            prepStat.setInt(1,id);
            String  surname = resSet.getString("surname");
            String  fname = resSet.getString("fname");
            String  sname = resSet.getString("sname");
            String phnumber = resSet.getString("phnumber");
            Double balance = resSet.getDouble("balance");
            ss = new Subscriber(surname, fname, sname, phnumber, balance);}
        return ss;
    }

    //Find id by phone number
    public int findIDByPhnumber(String phnumber) throws ClassNotFoundException, SQLException {
        String statforexe = "SELECT id FROM Subscriber WHERE phnumber = ?;";
        int id;
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe); ResultSet resSet = prepStat.executeQuery()) {
            prepStat.setString(Integer.valueOf(1), phnumber);
            id = resSet.getInt("id");
        }
        return id;
    }

    //Find Subscriber by phone number
    public Subscriber findSubscrByPhnumber(String phnumber) throws ClassNotFoundException, SQLException {
        String statforexe = "SELECT * FROM Subscriber WHERE phnumber = ?;";
        Subscriber ss;
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe); ResultSet resSet = prepStat.executeQuery()){
        prepStat.setString(1,phnumber);
        String  surname = resSet.getString("surname");
        String  fname = resSet.getString("fname");
        String  sname = resSet.getString("sname");
        Double balance = resSet.getDouble("balance");
        ss = new Subscriber(surname, fname, sname, phnumber, balance);}
        return ss;
    }

    public void findSubscr (String str, DefaultListModel listModel) throws SQLException {
        if (str.isEmpty())
            listModel.addElement("Пожалуйста, введите запрос");
        else {
            String statforexe = "SELECT * FROM Subscriber WHERE (surname like ?) or (fname like ?) or (sname like ?) or (phnumber like ?);";
            try(PreparedStatement prepStat = conn.prepareStatement(statforexe); ResultSet resSet = prepStat.executeQuery()) {
                prepStat.setString(1, "%" + str + "%");
                prepStat.setString(2, "%" + str + "%");
                prepStat.setString(3, "%" + str + "%");
                prepStat.setString(4, "%" + str + "%");
                while (resSet.next()) {
                    String surname = resSet.getString("surname");
                    String fname = resSet.getString("fname");
                    String sname = resSet.getString("sname");
                    String phnumber = resSet.getString("phnumber");
                    Double balance = resSet.getDouble("balance");
                    listModel.addElement(phnumber + ' ' + surname + ' ' + fname + ' ' + sname + ' ' + balance);
                }
            }
            if (listModel.isEmpty())
                listModel.addElement("Совпадений не найдено");
        }
    }

    public void changeBalance(String phNumber, Double cost) throws SQLException {
        String statforexe = "SELECT id, balance FROM Subscriber WHERE phnumber = ?;";
        String statforexe2 = "UPDATE Subscriber SET balance = ? WHERE id = ?;";
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe); ResultSet resSet = prepStat.executeQuery();
            PreparedStatement prepStat2 = conn.prepareStatement(statforexe2);) {
            prepStat.setString(1, phNumber);
            int id = resSet.getInt("id");
            double balance = resSet.getDouble("balance");
            balance = balance - cost;
            prepStat.setDouble(1, balance);
            prepStat.setInt(2, id);
            prepStat.executeUpdate();
        }
    }

    public void changeBalance (Subscriber ss, Double cost) throws SQLException {
        changeBalance(ss.getPhnumber(), cost);
    }

    //Delete string from table
    public void deleteFromSubscriber(Subscriber ss) throws SQLException {
        String statforexe = "DELETE FROM Subscriber WHERE phnumber = ?;";
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe)) {
            prepStat.setString(1, ss.getPhnumber());
            prepStat.executeUpdate();
        }
    }

    public void deleteFromSubscriberByID (int id) throws SQLException {
        String statforexe = "DELETE FROM Subscriber WHERE id = ?;";
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe)) {
            prepStat.setInt(1, id);
            prepStat.executeUpdate();
        }
    }

    //Find isFreeByPhNumber
    public boolean findIsFreeByPhnumber(String phnumber) throws ClassNotFoundException, SQLException {
        String statforexe = "SELECT idFree FROM Subscriber WHERE phnumber = ?;";
        boolean isFree;
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe); ResultSet resSet = prepStat.executeQuery();) {
            prepStat.setString(1, phnumber);
            isFree = resSet.getBoolean("isFree");
        }
        return isFree;
    }

    public void setIsFree (boolean isFree, int id) throws SQLException {
        String statforexe = "UPDATE Subscriber SET ifFree = ? WHERE id = ?;";
        try(PreparedStatement prepStat = conn.prepareStatement(statforexe)) {
            prepStat.setBoolean(1, isFree);
            prepStat.setInt(2, id);
            prepStat.executeUpdate();
        }
    }

}
