package com.github.tripville.service;


import org.springframework.data.repository.query.Param;

import com.github.tripville.model.DriverHistory;


public interface DriverService {
	DriverHistory save(DriverHistory driverHistory);
	DriverHistory findDriverHistoryForTrip(String tripId, String passengerId);
	
}

