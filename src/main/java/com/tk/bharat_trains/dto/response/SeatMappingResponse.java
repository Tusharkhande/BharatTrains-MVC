package com.tk.bharat_trains.dto.response;

import java.sql.Date;
import java.util.List;

import com.tk.bharat_trains.model.Train;

import lombok.Data;

@Data
public class SeatMappingResponse {
	private int id;

    private String trainId;

    private String station;

    private List<Integer> seats;

    private Date journeyDate;
}
