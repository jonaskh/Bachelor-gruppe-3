package no.ntnu.bachelor_group3.dataaccessevaluation;

import com.informix.jdbcx.IfxDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import com.informix.jdbcx.IfxDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class DataAccessEvaluationApplication {
	private static final Logger logger = LoggerFactory.getLogger(DataAccessEvaluationApplication.class);

	public static void main(String[] args) {
		Connection conn = null;
		try {
			// Load the Informix JDBC driver
			Class.forName("com.informix.jdbc.IfxDriver");

			// Connect to the Informix database
			conn = DriverManager.getConnection("jdbc:informix-sqli://localhost:9088/informixdb", "informix", "in4mix");

			// Create a statement
			Statement stmt = conn.createStatement();

			// Execute a query
			ResultSet rs = stmt.executeQuery("SELECT count(*) FROM slsls");

			// Display the results
			while (rs.next()) {
				int count = rs.getInt(1);
				logger.info("Number of tables: {}", count);
			}
		} catch (ClassNotFoundException e) {
			logger.error("Error loading driver: {}", e.getMessage());
		} catch (SQLException e) {
			logger.error("Error connecting to database: {}", e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					logger.error("Error closing connection: {}", e.getMessage());
				}
			}
		}
	}
}

