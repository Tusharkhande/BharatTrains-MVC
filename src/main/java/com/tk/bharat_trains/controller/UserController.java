package com.tk.bharat_trains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tk.bharat_trains.model.Users;
import com.tk.bharat_trains.service.UserService;

import java.util.*;

@RestController
@RequestMapping("/api/auth/users")
public class UserController {

	@Autowired
	UserService service;

	@PostMapping("/register")
	public ResponseEntity<Users> register(@RequestBody Users user) {
		return service.saveUser(user);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Users> deleteUser(@PathVariable int userId) {
		return service.deleteUser(userId);
	}

	@GetMapping("/{userId}")
	public ResponseEntity<Users> getUser(@PathVariable int userId) {
		return service.getUser(userId);
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
