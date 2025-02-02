package com.tk.bharat_trains.thymeleaf.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
@RequestMapping("/bharattrains/auth")
@Slf4j
public class AuthController {
	@Autowired
	UserService service;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	JwtUtil jwtUtil;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute Users user, Model model) {
        System.out.println(user);
//        service.saveUser(user);
        return "redirect:/bharattrains/auth/register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
    	System.out.println("login");
        return "login";
    }
    
//    @PostMapping("/login")
//    public String loginUser(@ModelAttribute LoginRequest loginRequest, BindingResult result, HttpSession session) {
//    	System.out.println("loginRequest: "+loginRequest);
//    	if(result.hasErrors()) {
//    		System.out.println(result);
//    	}
//    	try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//			UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
//			log.info("user logged in: "+userDetails.getUsername());
//			session.setAttribute("user", service.getUserByUsername(userDetails.getUsername()));
//			return "redirect:/bharattrains/home";
//		}catch(Exception e) {
//			log.error("Exception occurred while logging in... " + e);
//            return "redirect:/bharattrains/auth/login";
//		}
//    }
}
