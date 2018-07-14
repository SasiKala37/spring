package com.bridgelabz.fundoonote.user.security;

import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class SecurityConfig {

	static final String KEY = "sasi";

	public String createToken(String id) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	
		Date date = new Date();

		JwtBuilder builder = Jwts.builder().setId(id).setIssuedAt(date).signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	public Claims parseJwt(String jwt) {
		return  Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody();
	}
}
