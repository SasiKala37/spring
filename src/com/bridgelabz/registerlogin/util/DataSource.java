/***
 * Purpose:Create the connection pooling to maintain the connections
 * 
 * @author SasiKala
 * @version 1.0
 * @since 21-06-2018
 * */
package com.bridgelabz.registerlogin.util;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "tiger";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/user?useSSL=false";
	private static final String DB_CLASS = "com.mysql.jdbc.Driver";

	private static DataSource dataSource;
	private ComboPooledDataSource comboPooledDataSource;

	/**
	 * consrtuctor to set the properties of the database such as name, url and
	 * password
	 * 
	 * @throws PropertyVetoException
	 */
	private DataSource() throws PropertyVetoException {
		comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setDriverClass(DB_CLASS);
		comboPooledDataSource.setJdbcUrl(DB_URL);
		comboPooledDataSource.setUser(DB_USERNAME);
		comboPooledDataSource.setPassword(DB_PASSWORD);

		comboPooledDataSource.setMinPoolSize(3);
		comboPooledDataSource.setAcquireIncrement(5);
		comboPooledDataSource.setMaxPoolSize(20);
		comboPooledDataSource.setMaxStatements(180);
	}

	/**
	 * Create the Single instance of the class
	 * 
	 * @return instance of the class
	 * @throws IOException
	 * @throws SQLException
	 * @throws PropertyVetoException
	 */
	public static DataSource getInstance() throws IOException, SQLException, PropertyVetoException {
		if (dataSource == null) {
			dataSource = new DataSource();
			return dataSource;
		} else {
			return dataSource;
		}
	}

	/**
	 * get the connection from combopooled data source
	 * 
	 * @return pooled connection instance
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		return this.comboPooledDataSource.getConnection();
	}

}
