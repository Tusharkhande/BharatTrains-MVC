package com.tk.bharat_trains.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
	private String trainId;
    private String station;
    private String departureTime;
    private String arrivalTime;
}
