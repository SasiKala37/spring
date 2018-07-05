package com.bridgelabz.registerlogin.controllers;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.bridgelabz.registerlogin.util.Utility;

/**
 * Servlet Filter implementation class ValidationFilter
 */
//@WebFilter("/ValidationFilter")
public class ValidationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public ValidationFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("filter");
		String password = request.getParameter("password");
		if (Utility.validate(password)) {
			chain.doFilter(request, response);
		} else {
			request.setAttribute("errMessage1",
					"not a valid password it should consists of 1 upper lower digit special characters and minimum length 8");
			request.getRequestDispatcher("registerform.jsp").include(request, response);
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
