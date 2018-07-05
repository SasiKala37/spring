package com.bridgelabz.registerlogin.repository;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bridgelabz.registerlogin.util.DataSource;

public class LoginDAO {
	
	public boolean isValidUser(String userName, String password)
			throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
		
		Connection connection = DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("select * from register");
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			if (resultSet.getString(4).equals(userName) && resultSet.getString(6).equals(password)) {
				return true;
			}
		}
		return false;
	}
}
