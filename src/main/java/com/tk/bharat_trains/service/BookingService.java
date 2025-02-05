package com.tk.bharat_trains.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tk.bharat_trains.dto.requests.BookingRequest;
import com.tk.bharat_trains.dto.requests.CancelRequest;
import com.tk.bharat_trains.dto.requests.EmailRequest;
import com.tk.bharat_trains.dto.response.CancelResponse;
import com.tk.bharat_trains.dto.response.SearchResponse;
import com.tk.bharat_trains.dto.response.SeatResponse;
import com.tk.bharat_trains.model.Booking;
import com.tk.bharat_trains.repository.BookingRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {
	@Autowired
	private BookingRepository bookingRepository;

	@Autowired
	private SeatService seatService;
	
	@Autowired
	private NotificationService notificationService;

	public ResponseEntity<Booking> bookTicket(SearchResponse searchResponse, int userId) {
		BookingRequest bookingRequest = new BookingRequest();
		bookingRequest.setSource(searchResponse.getSource());
		bookingRequest.setDestination(searchResponse.getDestination());
		bookingRequest.setTrainId(searchResponse.getTrainId());
		bookingRequest.setJourneyDate(searchResponse.getJourneyDate());

//        try {
//			ResponseEntity<SeatResponse> seatResponse = webclientBuilder.build().post()
//        			.uri("http://localhost:8084/api/train/seats/available")
//        			.bodyValue(bookingRequest)
//        			.retrieve()
//        			.toEntity(SeatResponse.class)
//        			.block();

		ResponseEntity<SeatResponse> seatResponse = seatService.isSeatAvailable(bookingRequest);
		
		log.info("Seatresponse seatstatus... "+seatResponse.getBody().isAvailable());
		
		Booking booking = new Booking();
		if (seatResponse != null && seatResponse.getBody().isAvailable()) {
			booking.setSource(bookingRequest.getSource());
			booking.setDestination(bookingRequest.getDestination());
			booking.setSeatId(seatResponse.getBody().getSeatId());
			booking.setBookingStatus("CONFIRMED");
			booking.setJourneyDate(bookingRequest.getJourneyDate());
			booking.setBookingDate(LocalDate.now());
			booking.setTrainId(bookingRequest.getTrainId());
			booking.setTrainName(searchResponse.getTrainName());
			booking.setUserId(userId);
			bookingRepository.save(booking);
			notificationService.sendMail(new EmailRequest("goswaa0@gmail.com", "khandetushar2001@gmail.com", "Hello"));
			log.info("booking successful!");
						
			return new ResponseEntity<>(booking, HttpStatus.CREATED);
		} else {
			log.warn("booking unsuccessful!");
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	public ResponseEntity<CancelResponse> cancelTicket(int bookingId) {
		Booking booking = bookingRepository.findByBookingId(bookingId);
		CancelResponse cancelResponse = new CancelResponse();
		if (booking != null) {
			if (booking.getBookingStatus().equals("CANCELLED")) {
				cancelResponse.setCancelStatus(true);
				cancelResponse.setMessage("Ticket already cancelled!");
				log.info("Ticket already cancelled!");
				return new ResponseEntity<>(cancelResponse, HttpStatus.OK);
			}

			booking.setBookingStatus("CANCELLED");

			CancelRequest cancelRequest = new CancelRequest();
			cancelRequest.setSeatId(booking.getSeatId());
			cancelRequest.setSource(booking.getSource());
			cancelRequest.setDestination(booking.getDestination());
			cancelRequest.setTrainId(booking.getTrainId());
			cancelRequest.setJourneyDate(booking.getJourneyDate());

			ResponseEntity<Boolean> seatCancelStatus = seatService.cancelTicket(cancelRequest);

			if (seatCancelStatus != null && seatCancelStatus.getBody()) {
				bookingRepository.save(booking);
				cancelResponse.setCancelStatus(true);
				cancelResponse.setMessage("Ticket cancelled successfully!");
				log.info("Ticket cancelled successfully!");
				return new ResponseEntity<>(cancelResponse, HttpStatus.OK);
			}

		}

		cancelResponse.setCancelStatus(false);
		cancelResponse.setMessage("Ticket Cancellation Failed!");
		return new ResponseEntity<>(cancelResponse, HttpStatus.NOT_FOUND);
	}

	public ResponseEntity<List<Booking>> getBookingByUserId(int userId) {
		return new ResponseEntity<List<Booking>>(bookingRepository.findByUserId(userId), HttpStatus.OK);
	}
	
	public List<Booking> getRecent5Bookings() {
		return bookingRepository.findRecentBookings();
	}
	
}
