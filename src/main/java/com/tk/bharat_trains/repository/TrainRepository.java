package com.tk.bharat_trains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tk.bharat_trains.model.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, String> {
    Train findByTrainId(String trainId);
}