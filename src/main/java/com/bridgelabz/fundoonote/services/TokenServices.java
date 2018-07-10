package com.bridgelabz.fundoonote.services;

import java.sql.Date;

import com.bridgelabz.fundoonote.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenServices {
	final static String KEY = "sowjanya";

	public String createToken(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		String subject = user.getEmailId();
		String issuer = user.getUserName();
		Date date = new Date(0);

		JwtBuilder builder = Jwts.builder().setSubject(subject).setIssuedAt(date).setIssuer(issuer)
				.signWith(signatureAlgorithm, KEY);
		return builder.compact();

	}

	public void parseJwt(String Jwt) {
		Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(Jwt).getBody();
		System.out.println("subject-" + claims.getSubject());
		System.out.println("issuer-" + claims.getIssuer());
		System.out.println("date-" + claims.getIssuedAt());
	}
}
