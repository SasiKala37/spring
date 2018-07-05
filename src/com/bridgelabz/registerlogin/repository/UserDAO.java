package com.bridgelabz.registerlogin.repository;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bridgelabz.registerlogin.bean.UserBean;
import com.bridgelabz.registerlogin.util.DataSource;

public class UserDAO {

	public void saveUser(UserBean userBean) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
		/*Class.forName("com.mysql.jdbc.Driver");
		Connection	connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/user?useSSL=false","root","tiger");*/
		Connection connection = DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("insert into register values(?,?,?,?,?,?)");
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, userBean.getFirstName());
		preparedStatement.setString(3, userBean.getLastName());
		preparedStatement.setString(4, userBean.getUserName());
		preparedStatement.setString(5, userBean.getEmailId());
		preparedStatement.setString(6, userBean.getPassword());
		preparedStatement.executeUpdate();
		/*if(noOfRowsAffected > 0) {
			return true;
		} else {
			return false;
		}*/
	}
	public String getUserByUserName(String userName) throws SQLException, IOException, PropertyVetoException {
		Connection connection = DataSource.getInstance().getConnection();
		ResultSet resultSet=null;
		PreparedStatement preparedStatement=connection.prepareStatement("select user_name from register where user_name=?");
		preparedStatement.setString(1, userName);
		int no=preparedStatement.executeUpdate();
		if(no>0) {
			return userName;
		}
		else {
			return null;
		}
	}
}
