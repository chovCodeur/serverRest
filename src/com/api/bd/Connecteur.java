package com.api.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public final class Connecteur {
	
    private final static String URL = "jdbc:mysql://127.0.0.1:3306/alchimist?zeroDateTimeBehavior=convertToNull";
    //private final static String URL = "jdbc:mysql://193.190.248.155:3306/alchimist";

	private final static String LOGIN = "api";
    private final static String PASSWORD = "api";
	final static Logger logger = Logger.getLogger(Connecteur.class.getName());

    public static Connection getConnexion() throws SQLException {
    	logger.debug("MIPA getConnexion sur "+ URL);
    		try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

        final Connection con = DriverManager.getConnection(URL, LOGIN, PASSWORD);
        return con;
    }
}
