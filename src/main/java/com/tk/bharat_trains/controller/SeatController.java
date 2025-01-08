package com.tk.bharat_trains.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tk.bharat_trains.dto.AddStation;
import com.tk.bharat_trains.dto.BookingRequest;
import com.tk.bharat_trains.dto.CancelRequest;
import com.tk.bharat_trains.dto.SeatMappingResponse;
import com.tk.bharat_trains.dto.SeatResponse;
import com.tk.bharat_trains.model.StationToSeatMapping;
import com.tk.bharat_trains.service.SeatService;

@RestController
@RequestMapping("/api/train/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/available")
    public ResponseEntity<SeatResponse> isSeatAvailable(@RequestBody BookingRequest bookingRequest) {
        return seatService.isSeatAvailable(bookingRequest);
    }
    

    @PostMapping
	public ResponseEntity<List<StationToSeatMapping>> addStation(@RequestBody List<AddStation> station) {
		return seatService.saveStation(station);
	}
    
    @GetMapping
    public ResponseEntity<List<StationToSeatMapping>> getStations(){
    	return seatService.getStations();
    }
    
    @PostMapping("/cancel")
    public ResponseEntity<Boolean> cancelTicket(@RequestBody CancelRequest cancelRequest){
    	return seatService.cancelTicket(cancelRequest);
    }
}