package com.tk.bharat_trains.thymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tk.bharat_trains.dto.requests.SearchRequest;
import com.tk.bharat_trains.dto.response.SearchResponse;
import com.tk.bharat_trains.service.BookingService;

@Controller
@RequestMapping("/bharattrains")
public class BookingViewController {
	
	@Autowired
	BookingService bookingService;
	
	@GetMapping("/book")
	public String book(Model model) {
		List<SearchResponse> trainList = (List<SearchResponse>) model.asMap().get("trainList");
		SearchRequest searchRequest = (SearchRequest) model.asMap().get("searchRequest");
		if(trainList!=null) {
			model.addAttribute(trainList);
		}

		model.addAttribute("source", searchRequest.getSource());
		model.addAttribute("destination", searchRequest.getDestination());
		model.addAttribute("journeyDate", searchRequest.getJourneyDate());
		return "book";
	}
}
