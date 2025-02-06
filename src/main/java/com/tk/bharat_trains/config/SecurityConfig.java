package com.tk.bharat_trains.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tk.bharat_trains.repository.enums.Role;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	@Autowired
	RoleBasedAuthSuccessHandler successHandler;
	
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
		return http.csrf().disable()
//				.httpBasic().and()
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
				
				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/train/search**").hasRole("USER")
						.requestMatchers("/api/notify**").permitAll()
						.requestMatchers("/api/train/booking/**").hasRole("USER").requestMatchers("/api/train/**")
						.hasRole("ADMIN")
//						.requestMatchers("/api/train/search**", "/api/train/booking/**").hasRole("USER")
						.requestMatchers("/api/auth/**", "/bharattrains/auth/**").permitAll().anyRequest().authenticated())
				.formLogin().loginPage("/bharattrains/auth/login").successHandler(successHandler).permitAll().and()
				.logout(logout -> logout.logoutUrl("/bharattrains/auth/logout").invalidateHttpSession(true).clearAuthentication(true)
						.deleteCookies("JSESSIONID").logoutSuccessUrl("/bharattrains/auth/login?logout"))
				.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(MyUserDetailsService userDetailsService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userDetailsService);
		auth.setPasswordEncoder(encoder());
		return auth;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
	
}
