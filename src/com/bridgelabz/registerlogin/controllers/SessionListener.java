package com.bridgelabz.registerlogin.controllers;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
//@WebListener
public class SessionListener implements HttpSessionListener {
	ServletContext ctx=null;  
    static int total=0,current=0;  
    /**
     * Default constructor. 
     */
    public SessionListener() {
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent e)  { 
    	total++;  
        current++;  
       
        ctx=e.getSession().getServletContext();
        System.out.println("Session created");
        ctx.setAttribute("totalusers", total);  
        ctx.setAttribute("currentusers", current);
         
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent e)  { 
    	current--;  
        ctx.setAttribute("currentusers",current);  
        System.out.println("session destroyed");
    }
	
}
