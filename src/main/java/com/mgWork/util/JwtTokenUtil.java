package com.mgWork.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	private static final long JWT_TOKEN_VALIDITY = 5*60*60;
	@Value("${jwt.secret}")
	private String secret;

	
	public String generateToken(UserDetails details) {
//		System.out.println("----------"+secret+"--------------");
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder()
			.setClaims(claims)
			.setSubject(details.getUsername())
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
	}
	
	public String getUserNameFromToken(String jwtToken) {
//		System.out.println("----------getUserNameFromToken----method-----------");
		return getClaimFromToken(jwtToken, Claims::getSubject);
	}
	
	private <T> T getClaimFromToken(String token,Function<Claims, T> claimsResolver) {
//		System.out.println(secret+"----------"+token);
		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
//		System.out.println("--------------------"+claims+"-------------------------------------");
		return claimsResolver.apply(claims);
	}

	public boolean validateToken(String jwtToken, UserDetails details) {
		final String username = getUserNameFromToken(jwtToken);
		return username.equals(details.getUsername()) && !isTokenExpired(jwtToken);
	}

	private boolean isTokenExpired(String jwtToken) {
		final Date expiration = getExpirationDateFromToken(jwtToken);
		return expiration.before(new Date());
	}

	private Date getExpirationDateFromToken(String jwtToken) {
		
		return getClaimFromToken(jwtToken, Claims::getExpiration);
	}

}





















