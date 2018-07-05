package com.bridgelabz.registerlogin.controllers;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bridgelabz.registerlogin.models.User;
import com.bridgelabz.registerlogin.repository.UserDAO;

/**
 * Servlet implementation class LoginHttpServlet
 */
// @WebServlet("/LoginHttpServlet")
public class LoginHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	UserDAO userDAO = new UserDAO();
	User user1 = new User();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginHttpServlet() {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		try {			
			user1 = userDAO.getUserByUserName(userName);
			System.out.println(user1.getUserName()+"llll");
			if (user1.getUserName().equals(userName) && user1.getPassword().equals(password)) {
			
				request.setAttribute("userName", userName);
				HttpSession session = request.getSession();
				session.setAttribute("userName", userName);
				// setting session to expire in 30 mins
				session.setMaxInactiveInterval(30 * 60);
				Cookie userCookie = new Cookie("userName", userName);
				response.addCookie(userCookie);

				String encodedURL = response.encodeRedirectURL("loginsuccess.jsp");
				response.sendRedirect(encodedURL);
				// System.out.println("encodedURL: "+encodedURL);

				ServletContext ctx=request.getServletContext(); 
				int totalUsers=(Integer)ctx.getAttribute("totalusers"); 
				int currentUsers=(Integer)ctx.getAttribute("currentusers"); 
				System.out.println("Total Users: "+totalUsers);
                System.out.println("current users: "+currentUsers);
			} else {
				request.setAttribute("errMessage", "Either user name or password is incorrect.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		}

	}

}
