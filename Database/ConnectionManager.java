package Database;

import java.sql.*;

/**
 * A class to set up the connection to the lyon database.
 * 
 * @author Chloe Brinkman
 *
 */

public class ConnectionManager {

	static final String DB_URL = "jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/lyon";
	static final String USER = "lyon";
	static final String PASS = "4wzwdumguf";
	static Connection connection;

	public static Connection getConnection() throws Exception {

		try {

			System.out.println("Connecting...");
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected!");

		} catch (SQLException se) {
			se.printStackTrace();
		}
		return connection;

	}
}
