package com.bridgelabz.fundoonote.note.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bridgelabz.fundoonote.note.interceptor.NoteInterceptor;
import com.bridgelabz.fundoonote.user.util.Utility;

import io.jsonwebtoken.Claims;

public class NoteInterceptor extends HandlerInterceptorAdapter{
	static Logger logger = LoggerFactory.getLogger(NoteInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String tokenId= request.getHeader("userId");
		Claims id=Utility.parseJwt(tokenId);
		request.setAttribute("userId", id.getId());
		System.out.println("UserId:"+id.getId());
		logger.info("Before handling the request"+request.getRequestURI());
		
		return true;
	}
}
