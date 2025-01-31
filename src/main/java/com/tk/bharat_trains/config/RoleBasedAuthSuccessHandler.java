package com.tk.bharat_trains.config;

import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Component
public class RoleBasedAuthSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		List<String> roles = authentication.getAuthorities().stream()
				.map(grantedAuthority -> grantedAuthority.getAuthority()).collect(Collectors.toList());
		if (roles.contains("ROLE_ADMIN")) {
			response.sendRedirect("/bharattrains/admin/dashboard");
		} else {
			response.sendRedirect("/bharattrains/home");
		}
		
	}
	
}
