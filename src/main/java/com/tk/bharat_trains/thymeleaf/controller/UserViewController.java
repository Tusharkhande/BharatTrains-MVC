package com.tk.bharat_trains.thymeleaf.controller;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tk.bharat_trains.config.MyUserDetailsService;
import com.tk.bharat_trains.dto.requests.LoginRequest;
import com.tk.bharat_trains.model.Users;
import com.tk.bharat_trains.service.UserService;
import com.tk.bharat_trains.utils.JwtUtil;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/bharattrains/user")
@Slf4j
public class UserViewController {

	@Autowired
	UserService service;

    @GetMapping("/profile")
    public String userProfile(Model model, Principal principal) {
        String name = principal.getName();
        Users user = service.getUserByUsername(name);
        model.addAttribute("user", user);
        return "user/user-profile";
    }
    
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute Users user, RedirectAttributes redirectAttributes, HttpSession session) {
    	Users loggedUser = (Users)session.getAttribute("user");
    	try {
            service.updateUser(0, user);
            redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
    	}
    	catch(Exception e) {
    		redirectAttributes.addFlashAttribute("error", "Profile update failed");
    	}
    	
        return "redirect:/bharattrains/user/profile";
    }

//    @GetMapping("/{userId}")
//    public String getUser(@PathVariable int userId, Model model) {
//        model.addAttribute("user", service.getUser(userId).getBody());
//        return "user-details";
//    }
//
//    @GetMapping
//    public String getUsers(Model model) {
//        List<Users> users = service.getUsers();
//        model.addAttribute("users", users);
//        return "users-list";
//    }
}