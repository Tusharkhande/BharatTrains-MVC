package com.tk.bharat_trains.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bharattrains")
public class HomeController {

    
    @GetMapping("/home")
	public String showHomePage() {
		return "base";
	}

}
