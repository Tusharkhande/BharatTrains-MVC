package com.tk.bharat_trains.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tk.bharat_trains.dto.response.CancelResponse;
import com.tk.bharat_trains.dto.response.SearchResponse;
import com.tk.bharat_trains.model.Booking;
import com.tk.bharat_trains.service.BookingService;


@RestController
@RequestMapping("/api/train/booking")
public class BookingController {
	
	@Autowired
	BookingService bookingService;
	
	@PostMapping("/book/{userId}")
	public ResponseEntity<Booking> bookTicket(@RequestBody SearchResponse searchResponse, @PathVariable int userId){
		return bookingService.bookTicket(searchResponse, userId);
	}
	
	@PostMapping("/cancel/{bookingId}")
	public ResponseEntity<CancelResponse> cancelTicket(@PathVariable int bookingId){
		return bookingService.cancelTicket(bookingId);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Booking>> getBookingByUserId(@PathVariable int userId){
		return bookingService.getBookingByUserId(userId);
	}
	
	
	
}
