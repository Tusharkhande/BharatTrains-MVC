package com.tk.bharat_trains.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse {
    private String trainId;
    private String trainName;
    private String source;
    private String destination;
    private List<String> path;
    private String arrivalTime;
    private String departureTime;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate journeyDate;
    private String travelTime;
    private int availableSeats;
}
