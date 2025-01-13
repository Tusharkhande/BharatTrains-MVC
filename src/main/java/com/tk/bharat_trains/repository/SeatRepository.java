package com.tk.bharat_trains.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tk.bharat_trains.dto.response.SeatMappingResponse;
import com.tk.bharat_trains.model.StationToSeatMapping;


@Repository
public interface SeatRepository extends JpaRepository<StationToSeatMapping, Integer> {
	
	SeatMappingResponse findByStation(String stationName);

    List<StationToSeatMapping> findAllByTrainTrainId(String trainId);
}
