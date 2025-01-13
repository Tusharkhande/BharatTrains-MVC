package com.tk.bharat_trains.service;

import java.util.ArrayList;
import java.util.List;

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
		List<Route> routes = repository.findTrains(searchRequest.getSource(), searchRequest.getDestination());
		List<SearchResponse> searchResponses = new ArrayList<>();
		
		for(Route route: routes) {
			List<Stations> stations = repository.findByTrainId(route.getTrainId());
			List<String> path = new ArrayList<>();
			
			for(Stations station: stations) {
				path.add(station.getStation());
			}
			
//			String trainName = webClientBuilder.build().get()
//					.uri("http://localhost:8084/api/train/"+route.getTrainId())
//					.retrieve()
//					.bodyToMono(String.class)
//					.block();

            SearchResponse searchResponse = new SearchResponse(route.getTrainId(), trainService.getTrainName(route.getTrainId()), searchRequest.getSource(), searchRequest.getDestination(), path, route.getArrivalTime(), route.getDepartureTime(), searchRequest.getJourneyDate());
            searchResponses.add(searchResponse);
		}
		
		return searchResponses;
		
	}
	
	public ResponseEntity<List<Routes>> addRoute(List<Routes> routes){
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
}
