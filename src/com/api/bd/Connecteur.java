package com.api.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public final class Connecteur {

	//private static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");

    private final static String URL = "jdbc:mysql://127.0.0.1:3306/alchimist?zeroDateTimeBehavior=convertToNull";
    //private final static String URL = "jdbc:mysql://193.190.248.155:3306/alchimist";

	final static Logger logger = Logger.getLogger(Connecteur.class.getName());

    public static Connection getConnexion() throws SQLException {
    		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

            //final Connection con = DriverManager.getConnection(applicationProperties.getString("bd.connect.url"), applicationProperties.getString("bd.connect.user"), applicationProperties.getString("bd.connect.user"));
            final Connection con = DriverManager.getConnection(URL, "api","api");

    		return con;
    }
}
