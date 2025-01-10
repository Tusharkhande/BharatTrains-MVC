package com.tk.bharat_trains.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.tk.bharat_trains.model.Role;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DaoAuthenticationProvider auth) {
		return new MyUserDetailsService();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().httpBasic().and()
				.authorizeHttpRequests()
				.requestMatchers("/api/train/search**").hasRole("USER")
				.requestMatchers("/api/train/booking/**").hasRole("USER")
				.requestMatchers("/api/train/**").hasRole("ADMIN")
				.requestMatchers("/api/auth/**").permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider(MyUserDetailsService userDetailsService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(encoder());
		return auth;
	}
}
