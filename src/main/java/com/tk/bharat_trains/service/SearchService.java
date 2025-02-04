package com.tk.bharat_trains.service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tk.bharat_trains.dto.requests.SearchRequest;
import com.tk.bharat_trains.dto.response.Route;
import com.tk.bharat_trains.dto.response.SearchResponse;
import com.tk.bharat_trains.dto.response.Stations;
import com.tk.bharat_trains.model.Routes;
import com.tk.bharat_trains.repository.SearchRepository;


@Service
public class SearchService {
	@Autowired
	SearchRepository repository;
	
	@Autowired
	TrainService trainService;
	
	public List<SearchResponse> search(SearchRequest searchRequest){
		List<Route> routes = repository.findTrains(searchRequest.getSource(), searchRequest.getDestination(), searchRequest.getJourneyDate());
		List<SearchResponse> searchResponses = new ArrayList<>();
		System.out.println("routes..... " + routes);
		
		for(Route route: routes) {
			List<Stations> stations = repository.findByTrainIdAndDate(route.getTrainId(), searchRequest.getJourneyDate());
			List<String> path = new ArrayList<>();
			
			for(Stations station: stations) {
				path.add(station.getStation());
			}
			
//			String trainName = webClientBuilder.build().get()
//					.uri("http://localhost:8084/api/train/"+route.getTrainId())
//					.retrieve()
//					.bodyToMono(String.class)
//					.block();

            SearchResponse searchResponse = new SearchResponse(route.getTrainId(), trainService.getTrainName(route.getTrainId()), searchRequest.getSource(), searchRequest.getDestination(), path, route.getArrivalTime(), route.getDepartureTime(), searchRequest.getJourneyDate(), calculateTravelTime(route.getDepartureTime(), route.getArrivalTime()));
            searchResponses.add(searchResponse);
		}
		
		return searchResponses;
		
		
	}
	
	public ResponseEntity<List<Routes>> addMultipleRoutes(List<Routes> routes){
	    List<Routes> savedRoutes = new ArrayList<>();

		try {
			for (Routes route : routes) {
	            Routes savedRoute = repository.save(route);
	            savedRoutes.add(savedRoute);
	        }
	        return new ResponseEntity<>(savedRoutes, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<Routes> addRoute(Routes route) {
		try {
			Routes savedRoute = repository.save(route);
			return new ResponseEntity<>(savedRoute, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public List<String> getAllStations(){
		return repository.findAllStations();
	}
	
	public static String calculateTravelTime(String departureTime, String arrivalTime) {
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
	    try {
	        LocalTime departure = LocalTime.parse(departureTime, formatter);
	        LocalTime arrival = LocalTime.parse(arrivalTime, formatter);

	        System.out.println("Departure: " + departure);
	        System.out.println("Arrival: " + arrival);

	        // Handle overnight travel
	        if (arrival.isBefore(departure)) {
	            arrival = arrival.plusHours(24);
	        }

	        System.out.println("Adjusted Arrival: " + arrival);

	        Duration duration = Duration.between(departure, arrival);
	        long hours = duration.toHours();
	        long minutes = duration.toMinutes() % 60;

	        return String.format("%02d:%02d hrs", hours, minutes);
	    } catch (DateTimeParseException e) {
	        return "Invalid Time Format";
	    }
	}
	
}
