package com.bridgelabz.services;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.bean.UserBean;
import com.bridgelabz.repository.LoginDAO;

/**
 * Servlet implementation class LoginHttpServlet
 */
@WebServlet("/LoginHttpServlet")
public class LoginHttpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginHttpServlet() {
      
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName=request.getParameter("user_name");
		String password=request.getParameter("password");
		 
		UserBean userBean=new UserBean();
		 userBean.setUserName(userName);
		 userBean.setPassword(password);
		 
		 LoginDAO loginDAO=new LoginDAO();
		 
		try {
			 boolean success = loginDAO.isValidUser(userName, password);
			 if(success) {
				 request.setAttribute("registerBean", userBean);
				 response.sendRedirect("welcome.jsp");
			 }
			 else {
				 request.setAttribute("errMessage", success);
				 response.sendRedirect("login.jsp");
			 }
		} catch (SQLException | PropertyVetoException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		//doGet(request, response);
	}

}
