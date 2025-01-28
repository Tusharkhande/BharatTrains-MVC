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

import com.tk.bharat_trains.model.Train;
import com.tk.bharat_trains.service.TrainService;

@RestController
@RequestMapping("/api/train")
public class TrainController {
	
	@Autowired
	TrainService service;
	
	@PostMapping
	public ResponseEntity<Train> addTrain(@RequestBody List<Train> trains){
		return service.saveTrain(trains);
	}
	
	@GetMapping("/{trainId}")
	public String getTrainName(@PathVariable String trainId) {
		return service.getTrainName(trainId);
	}
}
