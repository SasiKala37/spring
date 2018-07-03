package com.bridgelabz.repository;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
 public boolean isValidUser(String userName,String password) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
	 Class.forName("com.mysql.jdbc.Driver");
	 connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user?useSSL=false","root","tiger");
	 // connection = DataSource.getInstance().getConnection();
	 preparedStatement=connection.prepareStatement("select user_name=? from register where password=?");
	 preparedStatement.setString(1, userName);
	 preparedStatement.setString(2, password);
	 boolean noOfRows=preparedStatement.execute();
	 if(noOfRows) {
		 return true;
	 }
	 else {
		 return false;
	 }
 }
}
