package com.tk.bharat_trains.repository;

import java.sql.Date;
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
	@Query("SELECT distinct new com.tk.bharat_trains.dto.response.Route(R1.trainId, R1.station, R1.departureTime, R2.arrivalTime) FROM Routes R1 INNER JOIN Routes R2 ON R1.trainId = R2.trainId WHERE R1.station = :source AND R2.station = :destination AND R1.journeyDate = :journeyDate")
	List<Route> findTrains(@Param("source") String source, @Param("destination") String destination, @Param("journeyDate") Date journeyDate);
	
	@Query("SELECT new com.tk.bharat_trains.dto.response.Stations(R.station) FROM Routes R WHERE R.trainId = :trainId and R.journeyDate = :journeyDate")
    List<Stations> findByTrainIdAndDate(@Param("trainId") String trainId,@Param("journeyDate") Date date);
	
	@Query("SELECT distinct R.station FROM Routes R")
    List<String> findAllStations();
}
