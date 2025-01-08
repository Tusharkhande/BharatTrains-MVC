package com.tk.bharat_trains.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class Routes {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "routes_seq_gen")
    @SequenceGenerator(name = "routes_seq_gen", sequenceName = "routes_seq", allocationSize = 1)
	private int routeId;
	
	private String trainId;
	
    private String station;
	
	private String arrivalTime;
	
	private String departureTime;
}
