package com.bridgelabz.registerlogin.services;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Application Lifecycle Listener implementation class SessionListener
 *
 */
@WebListener
public class SessionListener implements HttpSessionListener {
	ServletContext ctx=null;  
    static int total=0,current=0;  
    /**
     * Default constructor. 
     */
    public SessionListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent e)  { 
    	total++;  
        current++;  
       
        ctx=e.getSession().getServletContext();  
        ctx.setAttribute("totalusers", total);  
        ctx.setAttribute("currentusers", current);
        System.out.println("Session created"); 
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
