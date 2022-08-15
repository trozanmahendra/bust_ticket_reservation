package com.mgWork.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mgWork.util.JwtTokenUtil;

import io.jsonwebtoken.ExpiredJwtException;

public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = null;
		String username = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
//			System.out.println("-------jwtToken--------"+jwtToken+"---------------------");

			try {
//				System.out.println("---------------"+jwtTokenUtil+"---------------------");
				username = jwtTokenUtil.getUserNameFromToken(jwtToken);
//				System.out.println("------username---------"+username+"---------------------");
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Unable to get JWT token");

			} catch (ExpiredJwtException e) {
				throw new RuntimeException("Expired JWT token");
			}
		}
		
//		After getting the token we need to validate it
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails details = customUserDetailsService.loadUserByUsername(username);
			if (jwtTokenUtil.validateToken(jwtToken, details)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						details, null, details.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}
		}
		filterChain.doFilter(request, response);

	}

}
