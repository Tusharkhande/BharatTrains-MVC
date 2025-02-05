package com.tk.bharat_trains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tk.bharat_trains.model.Booking;

import java.util.List;


@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer>{
	Booking findByBookingId(int bookingId);
	
	List<Booking> findByUserId(int userId);
	
	@Query(value = " SELECT * FROM (SELECT b.* FROM booking b ORDER BY b.booking_date DESC) WHERE ROWNUM <= 5", nativeQuery = true)
	List<Booking> findRecentBookings();
}
