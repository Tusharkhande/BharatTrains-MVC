package com.tk.bharat_trains.dto.requests;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private String trainId;
    private String source;
    private String destination;
    private Date journeyDate;
}