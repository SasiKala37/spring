package com.bridgelabz.springrestloginapp.services;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.springrestloginapp.model.User;
import com.bridgelabz.springrestloginapp.repository.UserDAO;

@Service
public class UserService {
	@Autowired
	private UserDAO userDAO;

	public User findUserbyUserName(String userName) throws SQLException, IOException, PropertyVetoException {
		return userDAO.getUserByUserName(userName);
	}
	public void saveUser(User user) throws ClassNotFoundException, SQLException, IOException, PropertyVetoException {
		userDAO.saveUser(user);
	}
	
	public List<User> listOfUsers() throws SQLException, IOException, PropertyVetoException {
		return  userDAO.listOfUsers();
	}
}
