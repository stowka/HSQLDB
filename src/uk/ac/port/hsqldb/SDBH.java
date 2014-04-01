package uk.ac.port.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * 
 * @author Antoine De Gieter
 * @see org.hsqldb.*
 *
 */
public final class SDBH {
	public static Connection connection;
	private SDBH() {
		String url = "jdbc:hsqldb:file:";
		String database = "~/db/first"; // File that contains the database
		String driver = "org.hsqldb.jdbc.JDBCDriver";
		String username = "SA";
		String password = "";
		try {
			Class.forName(driver).newInstance();
			SDBH.connection = (Connection)DriverManager.getConnection(url + database, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Connection getConnection() {
		if (SDBH.connection == null)
			new SDBH();
		return SDBH.connection;
	}
}
