package com.tk.bharat_trains.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CancelRequest {
    private String trainId;
    private int seatId;
    private String source;
    private String destination;
}