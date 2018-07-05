package com.bridgelabz.registerlogin.controllers;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.registerlogin.models.User;
import com.bridgelabz.registerlogin.repository.UserDAO;

/**
 * Servlet implementation class RegisterHttpServlet
 */
@WebServlet(urlPatterns="/RegisterHttpServlet"/*loadOnStartup=1*/)
public class RegisterHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterHttpServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String firstName=request.getParameter("first_name");
		 String lastName=request.getParameter("last_name");
		 String userName=request.getParameter("user_name");
		 String emailId=request.getParameter("email");
		 String password=request.getParameter("password");
		 
		 User user=new User();
		 user.setFirstName(firstName);
		 user.setLastName(lastName);
		 user.setUserName(userName);
		 user.setEmailId(emailId);
		 user.setPassword(password);
		 
		 UserDAO userDAO=new UserDAO();
		 try {
			userDAO.saveUser(user);			
			response.sendRedirect("home.html");			
		} catch (SQLException | PropertyVetoException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
