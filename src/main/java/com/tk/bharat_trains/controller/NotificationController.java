package com.tk.bharat_trains.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tk.bharat_trains.dto.requests.EmailRequest;
import com.tk.bharat_trains.service.NotificationService;

@RestController
@RequestMapping("/api")
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@GetMapping("notify")
	public boolean sendMail(){
		return notificationService.sendMail(new EmailRequest("goswaa0@gmail.com", "khandetushar2001@gmail.com", "Hello"));
	}
	
}
