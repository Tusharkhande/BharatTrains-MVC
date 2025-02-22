package com.tk.bharat_trains.dto.requests;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddStation {
	private String trainId;
	private String station;
	private Date journeyDate;
    private List<Integer> seats;
}
