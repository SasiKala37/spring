package com.bridgelabz.fundoonote.configuaration;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
@Service
public class SecurityConfig {
	final static String KEY = "sasi";

	public String createToken(User user) {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		String subject = user.getEmailId();
		String issuer = user.getUserName();
		Date date = new Date();

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
