package com.bridgelabz.repository;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bridgelabz.bean.UserBean;

public class RegisterDAO {
	//private ResultSet resultSet = null;
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;

	public boolean registerUser(UserBean registerBean) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user?useSSL=false","root","tiger");
		//connection = DataSource.getInstance().getConnection();
		preparedStatement = connection.prepareStatement("insert into register values(?,?,?,?,?,?)");
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, registerBean.getFirstName());
		preparedStatement.setString(3, registerBean.getLastName());
		preparedStatement.setString(4, registerBean.getUserName());
		preparedStatement.setString(5, registerBean.getEmailId());
		preparedStatement.setString(6, registerBean.getPassword());
		int noOfRowsAffected = preparedStatement.executeUpdate();
		if(noOfRowsAffected > 0) {
			return true;
		} else {
			return false;
		}
	}
}
