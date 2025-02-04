package com.tk.bharat_trains.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tk.bharat_trains.dto.requests.AddStation;
import com.tk.bharat_trains.dto.requests.BookingRequest;
import com.tk.bharat_trains.dto.requests.CancelRequest;
import com.tk.bharat_trains.dto.response.SeatResponse;
import com.tk.bharat_trains.model.StationToSeatMapping;
import com.tk.bharat_trains.model.Train;
import com.tk.bharat_trains.repository.SeatRepository;
import com.tk.bharat_trains.repository.TrainRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeatService {
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	TrainRepository trainRepository;
	
	public ResponseEntity<SeatResponse> isSeatAvailable(BookingRequest bookingRequest) {
		SeatResponse seatResponse = new SeatResponse();
		
		List<StationToSeatMapping> route = seatRepository.findAllByTrainTrainId(bookingRequest.getTrainId(), bookingRequest.getJourneyDate());
		
		Train train = trainRepository.findByTrainId(bookingRequest.getTrainId());
		
		if(train==null || route.isEmpty()) {
			seatResponse.setAvailable(false);
			return new ResponseEntity<SeatResponse>(seatResponse, HttpStatus.NOT_FOUND);
		}
		
		int totalSeats = train.getSeatCount();
		
		log.info("Total Seats: "+totalSeats);
		
		Set<Integer> unavailableSeats = new HashSet<>();
		
		boolean track = false;
		
		for(StationToSeatMapping station : route) {
			if(station.getStation().equals(bookingRequest.getSource())) {
				track = true;
			}else if(station.getStation().equals(bookingRequest.getDestination())) {
				break;
			}
			if(track) {
				log.info("track " + track);
				unavailableSeats.addAll(station.getSeats());
			}
		}
		
		int availableSeat = -1;
		for(int i=1; i<=totalSeats; i++) {
			if(!unavailableSeats.contains(i)) {
				availableSeat = i;
				log.info("Seat available: " + i);
				seatResponse.setAvailable(true);
				seatResponse.setSeatId(availableSeat);
				break;
			}
		}
		
		if(availableSeat == -1) {
			log.warn("Seat not available!");
			seatResponse.setAvailable(false);
			return new ResponseEntity<SeatResponse>(seatResponse, HttpStatus.NOT_FOUND);
		}
		
		track = false;
		
		for(StationToSeatMapping station : route) {
			if(station.getStation().equals(bookingRequest.getSource())) {
				track = true;
			}else if(station.getStation().equals(bookingRequest.getDestination())) {
				break;
			}
			if(track) {
				List<Integer> seats = station.getSeats();
				if(seats.size() < totalSeats) {
					seats.add(availableSeat);
					station.setSeats(seats);
					log.info(station.toString());
					seatRepository.save(station);
				}else {
					seatResponse.setAvailable(false);
					return new ResponseEntity<SeatResponse>(seatResponse, HttpStatus.NOT_FOUND);
				}
			}
			
		}

		return new ResponseEntity<SeatResponse>(seatResponse, HttpStatus.OK);
	}

	public ResponseEntity<List<StationToSeatMapping>> saveMultipleStations(List<AddStation> stations) {
		try {
			List<StationToSeatMapping> savedStations = new ArrayList<>();
			stations.forEach(station -> {
				Train train = trainRepository.findById(station.getTrainId())
				        .orElseThrow(() -> new RuntimeException("Train not found with ID: " + station.getTrainId()));
				StationToSeatMapping mapping = new StationToSeatMapping();
				mapping.setTrain(train);
				mapping.setStation(station.getStation());
				mapping.setSeats(station.getSeats());
				mapping.setJourneyDate(station.getJourneyDate());
				seatRepository.save(mapping);
				savedStations.add(mapping);
			});
			return new ResponseEntity<>(savedStations,HttpStatus.CREATED);
		}catch(Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<StationToSeatMapping> saveStation(AddStation station) {
		try {
			Train train = trainRepository.findById(station.getTrainId())
					.orElseThrow(() -> new RuntimeException("Train not found with ID: " + station.getTrainId()));
			StationToSeatMapping mapping = new StationToSeatMapping();
			mapping.setTrain(train);
			mapping.setStation(station.getStation());
			mapping.setSeats(station.getSeats());
			mapping.setJourneyDate(station.getJourneyDate());
			seatRepository.save(mapping);
			return new ResponseEntity<>(mapping, HttpStatus.CREATED);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity<List<StationToSeatMapping>> getStations() {
		List<StationToSeatMapping> list = seatRepository.findAll();
		if(list == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
	}
	
	public ResponseEntity<Boolean> cancelTicket(CancelRequest cancelRequest){
		List<StationToSeatMapping> route = seatRepository.findAllByTrainTrainId(cancelRequest.getTrainId(), cancelRequest.getJourneyDate());
		boolean cancelStatus = false;
		if(route.isEmpty()) {
			log.error("empty");
			return new ResponseEntity<Boolean>(cancelStatus, HttpStatus.NOT_FOUND);
		}
		int seatToCancel = cancelRequest.getSeatId();
		log.info("seattocancel: " + seatToCancel);
		boolean track = false;
		
		for(StationToSeatMapping station : route) {
			if(station.getStation().equals(cancelRequest.getSource())) {
				track = true;
			}else if(station.getStation().equals(cancelRequest.getDestination())) {
				track = false;
				break;
			}
			if(track) {
				List<Integer> seats = station.getSeats();
				if(!seats.contains(Integer.valueOf(seatToCancel))) {
					cancelStatus = false;
					return new ResponseEntity<Boolean>(cancelStatus, HttpStatus.NOT_FOUND);
				}else {
					seats.remove(Integer.valueOf(seatToCancel));
					log.info(seats + "");
				}

				station.setSeats(seats);
				seatRepository.save(station);
				cancelStatus = true;
			}
			
		}
		return new ResponseEntity<>(cancelStatus, HttpStatus.OK);
	}
	
	public List<StationToSeatMapping> getSeatsByTrainId(String trainId) {
		return seatRepository.findAllByTrainTrainId(trainId);
	}

}
