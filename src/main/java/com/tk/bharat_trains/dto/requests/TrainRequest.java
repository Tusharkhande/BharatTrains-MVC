package com.tk.bharat_trains.dto.requests;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.tk.bharat_trains.model.Routes;
import com.tk.bharat_trains.model.StationToSeatMapping;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrainRequest {
	private String trainId;
	private String trainName;
	private int seatCount;
	private List<AddStation> seatMappings;
	private List<Routes> routes;

	public void formatTimes() {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm"); // 24-hour format
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a"); // 12-hour AM/PM format

		if (routes != null) {
			for (Routes route : routes) {
				route.setArrivalTime(formatTime(route.getArrivalTime(), inputFormatter, outputFormatter));
				route.setDepartureTime(formatTime(route.getDepartureTime(), inputFormatter, outputFormatter));
			}
		}
	}

	private String formatTime(String time, DateTimeFormatter input, DateTimeFormatter output) {
		if (time == null || time.isEmpty())
			return time; // Avoid NullPointerException
		return LocalTime.parse(time, input).format(output).toUpperCase();
	}
}
