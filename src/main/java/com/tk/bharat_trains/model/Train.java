package com.tk.bharat_trains.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Train {

    @Id
    private String trainId;
    private String trainName;
    private int seatCount;
    
}
