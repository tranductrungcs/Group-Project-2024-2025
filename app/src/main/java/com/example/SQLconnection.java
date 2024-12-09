package com.example;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLconnection {
    Connection con;
    protected static String classes = "com.mysql.jdbc.Driver";
    protected static String ip = "10.0.2.2";
    protected static String port = "3306";
    protected static String db = "androidapi";
    protected static String un = "root";
    protected static String password = "TAYthuyanh19(#";
    public Connection CONN(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        con = null;
        try {
            Class.forName(classes);
            String conURL = "jdbc:sqlserver://"+ip+ ":" +port+";" +db;
            con = DriverManager.getConnection(conURL, un, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return con;

    }
}
