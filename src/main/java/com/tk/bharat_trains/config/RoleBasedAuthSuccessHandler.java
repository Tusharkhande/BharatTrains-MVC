package com.tk.bharat_trains.config;

import org.springframework.stereotype.Component;

import com.tk.bharat_trains.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Component
@Slf4j
public class RoleBasedAuthSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		List<String> roles = authentication.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
		HttpSession session = request.getSession();
		session.setAttribute("user", userService.getUserByUsername(authentication.getName()));
		if (roles.contains("ROLE_ADMIN")) {
			log.info("Admin logged in");
			response.sendRedirect("/bharattrains/admin/dashboard");
		} else {
			log.info("User logged in {}", authentication.getName());
			response.sendRedirect("/bharattrains/home");
		}
		
	}
	
}
