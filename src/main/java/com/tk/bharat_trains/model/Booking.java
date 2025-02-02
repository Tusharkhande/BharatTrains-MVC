package com.tk.bharat_trains.model;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_seq_gen")
    @SequenceGenerator(name = "booking_seq_gen", sequenceName = "booking_seq", allocationSize = 1)
    private int bookingId;
    private String trainId;
    private int userId;
    private String trainName;
    private String source;
    private String destination;
    private int seatId;
    private String bookingStatus;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate journeyDate;
    private LocalDate bookingDate;
}
