package com.tk.bharat_trains.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tk.bharat_trains.service.UserService;

@Controller
@RequestMapping("/bharattrains/admin")
public class AdminViewController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/dashboard")
	public String showAdminDashboard() {
		return "/admin/admin-dashboard";
	}
	
	@GetMapping("/users")
	public String showUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "admin/manage-users";
	}
	
	@PostMapping("/deleteUser")
	public String deleteUser(@RequestParam("userId") int userId) {
		userService.deleteUser(userId);
		return "redirect:/bharattrains/admin/users";
	}
}
