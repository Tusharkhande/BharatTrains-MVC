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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tk.bharat_trains.dto.requests.BookingRequest;
import com.tk.bharat_trains.dto.requests.SearchRequest;
import com.tk.bharat_trains.dto.response.SearchResponse;
import com.tk.bharat_trains.model.Booking;
import com.tk.bharat_trains.model.Users;
import com.tk.bharat_trains.service.BookingService;
import com.tk.bharat_trains.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/bharattrains")
public class BookingViewController {
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/book")
	public String book(Model model, HttpSession session,Principal principal) {
		System.out.println(principal.getName());
		List<SearchResponse> searchResponse = (List<SearchResponse>) model.asMap().get("searchResponse");
		SearchRequest searchRequest = session.getAttribute("searchRequest")!=null?(SearchRequest) session.getAttribute("searchRequest"):null;
		List<String> stationList = session.getAttribute("stationList")!=null?(List<String>) session.getAttribute("stationList"):null;
		if(searchResponse!=null) {
			model.addAttribute(searchResponse);
		}
		if (searchRequest != null) {
			model.addAttribute("source", searchRequest.getSource());
			model.addAttribute("destination", searchRequest.getDestination());
			model.addAttribute("journeyDate", searchRequest.getJourneyDate());
		}
		System.out.println(searchResponse);
		System.out.println(stationList);
		model.addAttribute("searchRequest", searchRequest);
		model.addAttribute("stationList", stationList);
		
		return "book";
	}
	
	@PostMapping("/book")
	public String bookResponse(@ModelAttribute SearchResponse searchResponse, RedirectAttributes redirectAttributes ,Principal principal) {
		System.out.println(searchResponse);
		Users user = userService.getUserByUsername(principal.getName());
		System.out.println(user);
		Booking booking = bookingService.bookTicket(searchResponse, user.getUserId()).getBody();
		if(booking != null) {
			redirectAttributes.addFlashAttribute("booking", booking);
			return "redirect:/bharattrains/booking-successful";			
		}else {
			redirectAttributes.addFlashAttribute("error", "Sorry no seat available for the selected date try another train or date...");
			return "redirect:/bharattrains/book";
		}
		
	}
	
	@GetMapping("/booking-successful")
	public String bookingSuccessful(RedirectAttributes redirectAttributes, Model model) {
		Booking booking = (Booking) model.asMap().get("booking");
		model.addAttribute(booking);
		return "booking-successful";
	}
	
	@GetMapping("/user/bookings")
	public String bookings(Model model, Principal principal) {
		Users user = userService.getUserByUsername(principal.getName());
		List<Booking> bookings = bookingService.getBookingByUserId(user.getUserId()).getBody();
		model.addAttribute("bookings", bookings);
		return "user/bookings";
	}
	
	@PostMapping("/cancelBooking")
	public String cancelBooking(@RequestParam("bookingId") int bookingId, RedirectAttributes model) {
		bookingService.cancelTicket(bookingId);
		model.addFlashAttribute("success", "Booking Cancelled Successfully!");
		return "redirect:bharattrains/user/bookings";
	}
}
