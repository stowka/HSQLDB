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
			Statement stmt = conn.createStatement();
			
			/*
			 * 
			 */
			stmt.executeUpdate("DROP TABLE molecule IF EXISTS; DROP TABLE project IF EXISTS; DROP TRIGGER modify IF EXISTS");
			
			StringBuilder query = new StringBuilder();
			
			query.append("CREATE TABLE project (");
			query.append("id INT IDENTITY,");
			query.append("name VARCHAR(255),");
			query.append("description VARCHAR(4095),");
			query.append("creationTime TIMESTAMP DEFAULT NOW,");
			query.append("modificationTime TIMESTAMP DEFAULT NOW");
			query.append(");");
			System.out.println(query.toString());
			stmt.executeUpdate(query.toString());
			
			/*
			 * Empties the query
			 */			
			query.delete(0, query.toString().length());
			
			query.append("CREATE TABLE molecule (");
			query.append("id INT IDENTITY,");
			query.append("name VARCHAR(255),");
			query.append("smiles VARCHAR(1023),");
			query.append("projectId INT,");
			query.append("FOREIGN KEY (projectId) REFERENCES project(id)");
			query.append(");");
			System.out.println(query.toString());
			stmt.executeUpdate(query.toString());
			
			query.delete(0, query.toString().length());
			
			query.append("CREATE TRIGGER modify AFTER UPDATE ON molecule ");
			query.append("REFERENCING NEW AS newrow ");
			query.append("FOR EACH ROW ");
			query.append("BEGIN ATOMIC ");
			query.append("UPDATE project SET modificationTime = NOW ");
			query.append("WHERE id = newrow.projectId; ");
			query.append("END;");
			stmt.executeUpdate(query.toString());
			
			query.delete(0, query.toString().length());
			
			stmt.executeUpdate("INSERT INTO project (name, description) VALUES ('pro1', 'desc1'), ('pro2', 'desc2')");
			stmt.executeUpdate("INSERT INTO molecule (name, smiles, projectId) VALUES ('mol1', 'cc', 1), ('mol2', 'ccc', 0), ('mol3', 'cc=cc', 1)");
			
			stmt.executeUpdate("SHUTDOWN"); // Saves the modifications into the file
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

		}
	}
}
