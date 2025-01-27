package com.tk.bharat_trains.thymeleaf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tk.bharat_trains.dto.requests.SearchRequest;
import com.tk.bharat_trains.service.SearchService;

@Controller
@RequestMapping("/bharattrains")
public class SearchViewController {
	
	@Autowired
	SearchService searchService;
	
	@GetMapping("/search")
	public String search(Model model) {
		List<String> stationList =  searchService.getAllStations();
		System.out.println(stationList);
		model.addAttribute("stationList",stationList);
		model.addAttribute(new SearchRequest());
		return "search";
	}
	
	@PostMapping("/search")
	public String searchResponse(@ModelAttribute SearchRequest searchRequest, Model model, RedirectAttributes redirectAttributes) {
//		System.out.println(searchRequest);
//		System.out.println(searchService.search(searchRequest));
//		model.addAttribute("trainList", searchService.search(searchRequest));
		redirectAttributes.addFlashAttribute("trainList", searchService.search(searchRequest));
		redirectAttributes.addFlashAttribute("searchRequest", searchRequest);
		return "redirect:/bharattrains/book";
	}
}
