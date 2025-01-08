package com.tk.bharat_trains.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tk.bharat_trains.dto.SearchRequest;
import com.tk.bharat_trains.dto.SearchResponse;
import com.tk.bharat_trains.model.Routes;
import com.tk.bharat_trains.service.SearchService;

@RestController
@RequestMapping("/api/train")
public class SearchController {
	
	@Autowired
	SearchService searchService;
	
	@PostMapping("/search")
	public ResponseEntity<List<SearchResponse>> search(@RequestBody SearchRequest searchRequest){
		List<SearchResponse> response = searchService.search(searchRequest);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping("/routes")
	public ResponseEntity<List<Routes>> addRoute(@RequestBody List<Routes> routes){
		return searchService.addRoute(routes);
	}
	
}
