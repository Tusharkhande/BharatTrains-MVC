package com.tk.bharat_trains.thymeleaf.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tk.bharat_trains.dto.requests.SearchRequest;
import com.tk.bharat_trains.dto.response.SearchResponse;
import com.tk.bharat_trains.service.BookingService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bharattrains")
public class BookingViewController {
	
	@Autowired
	BookingService bookingService;
	
	@GetMapping("/book")
	public String book(Model model, HttpSession session,Principal principal) {
		System.out.println(principal.getName());
		List<SearchResponse> trainList = (List<SearchResponse>) model.asMap().get("trainList");
		SearchRequest searchRequest = session.getAttribute("searchRequest")!=null?(SearchRequest) session.getAttribute("searchRequest"):null;
		List<String> stationList = session.getAttribute("stationList")!=null?(List<String>) session.getAttribute("stationList"):null;
		if(trainList!=null) {
			model.addAttribute(trainList);
		}
		if (searchRequest != null) {
			model.addAttribute("source", searchRequest.getSource());
			model.addAttribute("destination", searchRequest.getDestination());
			model.addAttribute("journeyDate", searchRequest.getJourneyDate());
		}
		System.out.println(trainList);
		System.out.println(stationList);
		model.addAttribute("searchRequest", searchRequest);
		model.addAttribute("stationList", stationList);
		
		return "book";
	}
	
	@PostMapping("/book")
	public String bookResponse(@ModelAttribute SearchRequest searchRequest, Model model,Principal principal) {
		System.out.println(principal.getName());
		return "redirect:/bharattrains/book";
	}
}
