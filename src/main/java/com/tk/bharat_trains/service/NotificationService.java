package com.tk.bharat_trains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.tk.bharat_trains.dto.requests.EmailRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	public boolean sendMail(@RequestBody EmailRequest emailRequest) {
		try {
			log.info("initiating mail sending process");
			log.info(emailRequest.getBody() + emailRequest.getSubject() + emailRequest.getTo());
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setFrom("khandetushar2001@gmail.com");
			mail.setTo(emailRequest.getTo());
			mail.setSubject(emailRequest.getSubject());
			mail.setText(emailRequest.getBody());
			javaMailSender.send(mail);
			log.info("Notification Sent successfully...");
			return true;
		}catch(Exception e) {
			log.error("Error sending mail... :"+e);
			return false;
		}
	}
}
