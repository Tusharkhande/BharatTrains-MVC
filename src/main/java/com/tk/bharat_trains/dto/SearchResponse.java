package com.tk.bharat_trains.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

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
    private Date journeyDate;
}
