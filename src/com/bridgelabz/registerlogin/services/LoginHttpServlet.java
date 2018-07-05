package com.bridgelabz.registerlogin.services;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bridgelabz.registerlogin.repository.LoginDAO;

/**
 * Servlet implementation class LoginHttpServlet
 */
//@WebServlet("/LoginHttpServlet")
public class LoginHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginHttpServlet() {
      
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("welcome to login");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		//System.out.println(userName+"kkkkk");

		LoginDAO loginDAO = new LoginDAO();

		try {
			boolean success = loginDAO.isValidUser(userName, password);
			if (success) {
				
				request.setAttribute("userName", userName);
				HttpSession session = request.getSession();
				session.setAttribute("userName", userName);
				// setting session to expire in 30 mins
				session.setMaxInactiveInterval(30 * 60);
				Cookie user = new Cookie("userName", userName);
				response.addCookie(user);
				

				String encodedURL = response.encodeRedirectURL("loginsuccess.jsp");
				response.sendRedirect(encodedURL);
				
				 
				ServletContext ctx=request.getServletContext(); 
				int totalUsers=(Integer)ctx.getAttribute("totalusers"); 
				int currentUsers=(Integer)ctx.getAttribute("currentusers"); 
				System.out.println("Total Users: "+totalUsers);
				System.out.println("current users: "+currentUsers);
			} else {
				request.setAttribute("errMessage","Either user name or password is incorrect.");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
