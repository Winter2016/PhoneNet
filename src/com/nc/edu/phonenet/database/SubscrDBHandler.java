package com.nc.edu.phonenet.database;

        import com.nc.edu.phonenet.model.Subscriber;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.List;

/**
 * Created by Ксения on 3/20/2016.
 */
public class SubscrDBHandler {
        public static Connection conn;
        public static Statement statmt;
        public static ResultSet resSet;

        // Connection to database
        public static void Conn() throws ClassNotFoundException, SQLException
        {
            conn = null;
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:PhoneNetDB.s3db");
        }

        // Table creation
        public static void CreateTable() throws ClassNotFoundException, SQLException
        {
            statmt = conn.createStatement();
            statmt.execute("CREATE TABLE if not exists 'Subscriber' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'surname' text, 'fname' text, 'sname' text, 'phnumber' text, 'balance' real);");
        }

        //Filling table
        public static void WriteTable(Subscriber sc) throws SQLException
        {
            String surname = "'" + sc.getSurName() + "'";
            String fname = "'" + sc.getFirstName() + "'";
            String sname = "'" + sc.getSecondName() + "'";
            String phnumber ="'" + sc.getPhnumber() + "'";
            Double balance = sc.getBalance();
            String statforexe = "INSERT INTO 'Subscriber' ('surname', 'fname', 'sname', 'phnumber', 'balance') VALUES  (" + surname + ", " + fname + ", " + sname + ", " + phnumber + ", " + balance + ");";
            statmt.execute(statforexe);
        }

        public  static void WriteTable(List<Subscriber> sslist) throws SQLException
        {
            for(Subscriber ss : sslist)
            {
                WriteTable(ss);
            }
        }

        // Table output
        public static List<Subscriber> ReadTable() throws ClassNotFoundException, SQLException
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
        public  static int FindID(String phnumber) throws ClassNotFoundException, SQLException {
            resSet = statmt.executeQuery("SELECT id FROM Subscriber WHERE phnumber = " + "'" + phnumber + "';");
            return resSet.getInt("id");
        }

        //Find Subscriber by id
        public static Subscriber FindSubscr(int id) throws ClassNotFoundException, SQLException {
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

        // Close connection
        public static void CloseDB() throws ClassNotFoundException, SQLException
        {
            conn.close();
            resSet.close();
        }

    public static Connection getConn() {
        return conn;
    }
}

