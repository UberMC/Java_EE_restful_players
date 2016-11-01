package net.ubermc.players.database;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DatabaseManager {
    public Connection conn;
    private Logger log;
    private static DatabaseManager instance = new DatabaseManager();

    public static DatabaseManager getInstance() {
        return instance;
    }

    public void setup() {
        connect();
    }

    public Connection getMysqlConnection() {
        return this.conn;
    }

    public boolean connectToDB(String host, int port, String db, String user, String pass) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, pass);
            return true;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception");
            return false;
        } catch (SQLException e) {
        	 System.out.println("Couldn't connect to MySQL database. Stopping...\n" + e.getMessage());
        }
        return false;
    }

    public PreparedStatement createStatement(String query) {
        int times = 0;
        PreparedStatement p = null;
        try {
            times++;
            p = this.conn.prepareStatement(query, 1);
        } catch (SQLException e) {
            if (times == 5) {
                return null;
            }
            connect();
        }

        return p;
    }

    public Statement createStatement() {
        try {
            return this.conn.createStatement();
        } catch (SQLException e) {
        }
        return null;
    }

    public boolean connect() {
        try {
            if (this.conn != null) {
                if (this.conn.isValid(5)) {
                    return true;
                }
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        
       
        String host = "localhost";
        int port = 3306;
        String db = "authcraft";
        String user = "username";
        String pass = "password";
        return connectToDB(host, port, db, user, pass);
    }

}