package com.tk.bharat_trains.thymeleaf.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.MergedAnnotations.Search;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tk.bharat_trains.dto.requests.AddStation;
import com.tk.bharat_trains.dto.requests.TrainRequest;
import com.tk.bharat_trains.model.Routes;
import com.tk.bharat_trains.model.StationToSeatMapping;
import com.tk.bharat_trains.model.Train;
import com.tk.bharat_trains.service.SearchService;
import com.tk.bharat_trains.service.SeatService;
import com.tk.bharat_trains.service.TrainService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/bharattrains")
public class TrainViewController {
	@Autowired
	TrainService trainService;

	@Autowired
	SeatService seatService;

	@Autowired
	SearchService searchService;

//	@GetMapping()
//	public String showTrains() {
//		return "trains";
//	}

	@GetMapping("/admin/trains")
	public String getAllTrainsWithPaths(Model model) {
		List<Train> trains = trainService.getAllTrains();
		Map<String, List<String>> trainPaths = trains.stream()
				.collect(Collectors.toMap(Train::getTrainId, train -> seatService.getSeatsByTrainId(train.getTrainId())
						.stream().map(StationToSeatMapping::getStation).distinct().collect(Collectors.toList())));

		model.addAttribute("trains", trains);
		model.addAttribute("trainPaths", trainPaths);

		return "all-trains";
	}

	@GetMapping("/admin/addTrain")
	public String showTrainForm(Model model) {
		model.addAttribute("trainRequest", new TrainRequest());
		return "add-train";
	}

	@PostMapping("/admin/save-train")
	public String saveTrain(@ModelAttribute TrainRequest trainRequest) {
		Train train = new Train(trainRequest.getTrainId(), trainRequest.getTrainName(), trainRequest.getSeatCount());
		System.out.println(trainRequest);
		trainService.saveTrain(List.of(train));
		trainRequest.formatTimes();
		for (Routes route : trainRequest.getRoutes()) {
			route.setTrainId(train.getTrainId());
			searchService.addRoute(route);
		}
		for (AddStation addStation : trainRequest.getSeatMappings()) {
			addStation.setTrainId(train.getTrainId());
			seatService.saveStation(addStation);
		}
		return "redirect:/bharattrains/admin/trains";
	}
}
