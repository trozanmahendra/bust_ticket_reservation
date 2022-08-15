package com.mgWork.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mgWork.entitys.Ticket;
import com.mgWork.generators.StringPrefixedSequenceIdGenerator;
import com.mgWork.security.CustomUserDetailsService;
import com.mgWork.security.JwtRequestFilter;

@SuppressWarnings("deprecation")
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService CustomerUserDetailsService;

	@Bean
	public JwtRequestFilter authenticationJwtFilter() {
		return new JwtRequestFilter();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable().authorizeRequests()
				.antMatchers("/admin/register", "/admin/login", "/", "/cust/register", "/cust/login","/cust/searchroutes","/admin/auth","/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
				.mvcMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers("/cust/**").hasAnyAuthority("ADMIN", "USER")
			    .anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().addFilterBefore(authenticationJwtFilter(), UsernamePasswordAuthenticationFilter.class);
			http.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(CustomerUserDetailsService);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return super.authenticationManagerBean();
	}

	@Bean
	public Date dateBean() {
		return new Date();

	}

	@Bean
	public Ticket ticket() {
		return new Ticket();
	}

	@Bean
	public StringPrefixedSequenceIdGenerator stringPrefixedSequenceIdGenerator() {
		return new StringPrefixedSequenceIdGenerator();
	}

}
