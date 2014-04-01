package uk.ac.port.hsqldb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
	public static void main(String[] argv) {
		Connection conn = SDBH.getConnection();
		try {
			PreparedStatement pstm = conn.prepareStatement("INSERT INTO t1 (col2, col3) VALUES (?, CURRENT_TIMESTAMP)");
			pstm.setString(1, "Salut");
			pstm.execute();
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery("SELECT * FROM t1");
			while(rset.next()){
				System.out.println(rset.getInt("col1") + "\t" + rset.getString("col2") + "\t" + rset.getString("col3"));
			}
			stmt.executeUpdate("SHUTDOWN"); // Saves the modifications into the file
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
