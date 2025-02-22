package com.tk.bharat_trains.thymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tk.bharat_trains.model.Users;
import com.tk.bharat_trains.service.BookingService;
import com.tk.bharat_trains.service.SearchService;
import com.tk.bharat_trains.service.SeatService;
import com.tk.bharat_trains.service.TrainService;
import com.tk.bharat_trains.service.UserService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/bharattrains/admin")
public class AdminViewController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	BookingService bookingService;
	
	@Autowired
	TrainService trainService;
	
	@Autowired
	SeatService seatService;
	
	@Autowired
	SearchService searchService;
	
	@GetMapping("/dashboard")
	public String showAdminDashboard(Model model) {
		model.addAttribute("bookingList", bookingService.getRecent5Bookings());
		model.addAttribute("totalUsers", userService.getUsers().size());
		model.addAttribute("totalTrains", trainService.getAllTrains().size());
		model.addAttribute("totalBookings", bookingService.getAllBookings().size());
		model.addAttribute("totalRevenue", 12);

		return "/admin/admin-dashboard";
	}
	
	@GetMapping("/users")
	public String showUsers(Model model) {
		model.addAttribute("users", userService.getUsers());
		return "admin/manage-users";
	}
    
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute Users user, RedirectAttributes redirectAttrs) {
      userService.saveUser(user);
      redirectAttrs.addFlashAttribute("success", "User added successfully!");
      return "redirect:/bharattrains/admin/users";
    }
	
	@PostMapping("/deleteUser")	
	public String deleteUser(@RequestParam("userId") int userId, RedirectAttributes redirectAttributes) {
		userService.deleteUser(userId);
		redirectAttributes.addFlashAttribute("success", "User Deletion successful!");
		return "redirect:/bharattrains/admin/users";
	}
	
	@PostMapping("/deleteTrain/{trainId}")
	@Transactional
	public String deleteTrain(@PathVariable("trainId") String trainId) {
		System.out.println(trainId);
		seatService.deleteSeatMappingsByTrainId(trainId);
		searchService.deleteRoutesByTrainId(trainId);
		trainService.deleteTrain(trainId);
		return "redirect:/bharattrains/admin/dashboard";
	}
	
	@GetMapping("/bookings")
	public String showBookings(Model model) {
		model.addAttribute("bookings", bookingService.getAllBookings());
		return "admin/manage-bookings";
	}
}
