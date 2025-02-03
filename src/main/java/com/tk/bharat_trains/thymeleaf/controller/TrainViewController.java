package com.tk.bharat_trains.thymeleaf.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tk.bharat_trains.model.StationToSeatMapping;
import com.tk.bharat_trains.model.Train;
import com.tk.bharat_trains.service.SeatService;
import com.tk.bharat_trains.service.TrainService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/bharattrains/trains")
public class TrainViewController {
	@Autowired
	TrainService trainService;
	
	@Autowired
	SeatService seatService;
	
//	@GetMapping()
//	public String showTrains() {
//		return "trains";
//	}
	
	@GetMapping
//	public String getAllTrainsWithDetails(Model model) {
//        List<Train> trains = trainService.getAllTrains();
//        Map<String, List<StationToSeatMapping>> trainSeatMappings = trains.stream()
//                .collect(Collectors.toMap(
//                        Train::getTrainId,
//                        train -> seatService.getSeatsByTrainId(train.getTrainId())
//                ));
//
//        model.addAttribute("trains", trains);
//        model.addAttribute("trainSeatMappings", trainSeatMappings);
//
//        return "all-trains"; // This renders all-trains.html
//    }
//	
	public String getAllTrainsWithPaths(Model model) {
        List<Train> trains = trainService.getAllTrains();
        Map<String, List<String>> trainPaths = trains.stream()
                .collect(Collectors.toMap(
                        Train::getTrainId,
                        train -> seatService.getSeatsByTrainId(train.getTrainId())
                                .stream()
                                .map(StationToSeatMapping::getStation)
                                .distinct()
                                .collect(Collectors.toList())
                ));

        model.addAttribute("trains", trains);
        model.addAttribute("trainPaths", trainPaths);

        return "all-trains"; // This renders all-trains.html
    }
}
