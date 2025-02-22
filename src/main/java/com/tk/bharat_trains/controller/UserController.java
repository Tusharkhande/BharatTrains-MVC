package com.tk.bharat_trains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tk.bharat_trains.config.MyUserDetailsService;
import com.tk.bharat_trains.dto.requests.LoginRequest;
import com.tk.bharat_trains.model.Users;
import com.tk.bharat_trains.service.UserService;
import com.tk.bharat_trains.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/users")
@Slf4j
public class UserController {

	@Autowired
	UserService service;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<Users> register(@RequestBody Users user) {
		return service.saveUser(user);
	}
	
	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
			List<String> roles = userDetails.getAuthorities().stream()
					.map(grantedAuthority -> grantedAuthority.getAuthority())
					.collect(Collectors.toList());
			System.out.println(roles);
			String jwt = jwtUtil.generateToken(userDetails.getUsername());
			return new ResponseEntity<String>(jwt, HttpStatus.OK);
		}catch(Exception e) {
			log.error("Exception occurred while createAuthenticationToken " + e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable int userId) {
		service.deleteUser(userId);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Users> getUser(@PathVariable int userId) {
		Users user =  service.getUserById(userId);
		if(user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	@GetMapping("")
	public List<Users> getUsers() {
		return service.getUsers();
	}
	
	@PutMapping("{userId}")
	public ResponseEntity<Users> updateUser(@PathVariable int userId, @RequestBody Users user){
		return service.updateUser(userId, user);
	}
}
