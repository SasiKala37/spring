package com.bridgelabz.springrestloginapp.repository;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.bridgelabz.springrestloginapp.model.User;
import com.bridgelabz.springrestloginapp.util.DataSource;
@Repository
public class UserDAO {

	public void saveUser(User userBean) throws SQLException, IOException, PropertyVetoException, ClassNotFoundException {
		
		Connection connection = DataSource.getInstance().getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("insert into register values(?,?,?,?,?,?)");
		ResultSet resultSet=null;
		preparedStatement.setInt(1, 0);
		preparedStatement.setString(2, userBean.getFirstName());
		preparedStatement.setString(3, userBean.getLastName());
		preparedStatement.setString(4, userBean.getUserName());
		preparedStatement.setString(5, userBean.getEmailId());
		preparedStatement.setString(6, userBean.getPassword());
		preparedStatement.executeUpdate();
		closeResources(connection, preparedStatement, resultSet);
		
	}
	public User getUserByUserName(String userName) throws SQLException, IOException, PropertyVetoException {
		Connection connection = DataSource.getInstance().getConnection();
		ResultSet resultSet=null;
		User user=null;
		PreparedStatement preparedStatement=connection.prepareStatement("select * from register where user_name=?");
		preparedStatement.setString(1, userName);
		resultSet=preparedStatement.executeQuery();
		if(resultSet.next()) {
			 user=new User();
			user.setUserName(resultSet.getString(4));
			user.setPassword(resultSet.getString(6));
			
		}
		closeResources(connection, preparedStatement, resultSet);
		return user;
	}
	public void closeResources(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet) throws SQLException {
		if(connection!=null) {
			connection.close();
		}
		if(preparedStatement!=null) {
			preparedStatement.close();
		}
		if(resultSet!=null) {
			resultSet.close();
		}
		
	}
}
