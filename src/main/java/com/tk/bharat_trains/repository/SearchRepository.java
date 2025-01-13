package com.tk.bharat_trains.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tk.bharat_trains.dto.response.Route;
import com.tk.bharat_trains.dto.response.Stations;
import com.tk.bharat_trains.model.Routes;

@Repository
public interface SearchRepository extends JpaRepository<Routes, Integer>{
	@Query("SELECT new com.tk.bharat_trains.dto.response.Route(R1.trainId, R1.station, R1.arrivalTime, R1.departureTime) FROM Routes R1 INNER JOIN Routes R2 ON R1.trainId = R2.trainId WHERE R1.station = :source AND R2.station = :destination")
	List<Route> findTrains(@Param("source") String source, @Param("destination") String destination);
	
	@Query("SELECT new com.tk.bharat_trains.dto.response.Stations(R.station) FROM Routes R WHERE R.trainId = :trainId")
    List<Stations> findByTrainId(String trainId);
}
