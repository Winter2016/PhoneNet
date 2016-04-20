package com.nc.edu.phonenet.DAO;

import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ксения on 3/21/2016.
 */
public class CallRegisterDAO extends DAO {

    // CallRegister table creation
    public void createTableCallRegister () throws ClassNotFoundException, SQLException {
        try (Statement statmt = conn.createStatement()) {
            statmt.execute("CREATE TABLE if not exists 'CallRegister' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'id_out' INTEGER FOREIGH KEY REFERENCES Subscriber(id), 'id_in' INTEGER FOREIGH KEY REFERENCES Subscriber(id), 'cost' real);");
        }
    }

    //Filling CallRegister table
    public void writeTableCallRegister(CallRegister callreg) throws SQLException, ClassNotFoundException {
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        Integer id_out = subscriberDAO.findIDByPhnumber(callreg.getOutCaller().getPhnumber());
        Integer id_in = subscriberDAO.findIDByPhnumber(callreg.getInCaller().getPhnumber());
        String statforexe = "INSERT INTO 'CallRegister' ('id_out', 'id_in', 'cost') VALUES  (?, ?, ?);";
        try (PreparedStatement  prepStat = conn.prepareStatement(statforexe)) {
        prepStat.setInt(1, id_out);
        prepStat.setInt(2, id_in);
        prepStat.setDouble(3, callreg.getCost());
        prepStat.executeUpdate();
    }
    }

    public void writeTableCallRegister (List<CallRegister> crlist) throws SQLException, ClassNotFoundException {
        for(CallRegister cr : crlist)
        {
            writeTableCallRegister(cr);
        }
    }

    // Table output
    public List<CallRegister> readTableCallRegister() throws ClassNotFoundException, SQLException
    {
        List<CallRegister> crlist = new ArrayList<>();
        SubscriberDAO subscriberDAO = new SubscriberDAO();
        try(Statement statmt = conn.createStatement();
        ResultSet resSet = statmt.executeQuery("SELECT * FROM CallRegister")) {
            while (resSet.next()) {
                int id_out = resSet.getInt("id_out");
                int id_in = resSet.getInt("id_in");
                Double cost = resSet.getDouble("cost");
                Subscriber outScr = subscriberDAO.findSubscrByID(id_out);
                Subscriber inScr = subscriberDAO.findSubscrByID(id_in);
                crlist.add(new CallRegister(outScr, inScr, cost));
            }
        }
        return crlist;
    }

    //Delete string from table

    public void deleteFromCallRegisterByID (int id) throws SQLException {
        String statforexe = "DELETE FROM CallRegister WHERE id = ?;";
        try( PreparedStatement  prepStat = conn.prepareStatement(statforexe)) {
            prepStat.setInt(1, id);
            prepStat.executeUpdate();
        }
    }

}
