package uk.ac.port.hsqldb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] argv) {
		Connection conn = SDBH.getConnection();
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE atom IF EXISTS");
			stmt.executeUpdate(
				"CREATE TABLE atom (" + 
					"z INT PRIMARY KEY COMMENT 'proton number'," +
					"a INT COMMENT 'nucleon number'," +
					"n INT COMMENT 'neutron number'," +
					"symbol VARCHAR(2)" + 
				")"
			);
			stmt.executeUpdate("INSERT INTO atom VALUES (1, 'H'), (12, 'C')");
			ResultSet rset = stmt.executeQuery("SELECT * FROM atom");
			while(rset.next()){
				System.out.println(rset.getString("symbol"));
			}
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
