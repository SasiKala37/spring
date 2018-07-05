package com.bridgelabz.registerlogin.services;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bridgelabz.registerlogin.bean.UserBean;
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
		 
		 UserBean userBean=new UserBean();
		 userBean.setFirstName(firstName);
		 userBean.setLastName(lastName);
		 userBean.setUserName(userName);
		 userBean.setEmailId(emailId);
		 userBean.setPassword(password);
		 
		 UserDAO userDAO=new UserDAO();
		 try {
			// boolean success;
			userDAO.saveUser(userBean);
			/*if(success) {
				System.out.println(success);*/
				response.sendRedirect("home.html");
			//}
			/*else {
				 request.setAttribute("errMessage", success);
				 response.sendRedirect("registerform.jsp");
			}*/
		} catch (SQLException | PropertyVetoException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
		 
		//doGet(request, response);
	}

}
