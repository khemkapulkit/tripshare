package com.github.tripville.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tripville.model.DriverHistory;
import com.github.tripville.repository.DriverRepository;

@Service("DriverService")
public class DriverServiceImpl implements DriverService {

	@Autowired
	private DriverRepository driverRepository;
	
	@Transactional
	public DriverHistory save(DriverHistory driverHistory) {
		return driverRepository.save(driverHistory);
	}
	
	//@Override
	public DriverHistory findDriverHistoryForTrip(String tripId,String passengerId) {
		return driverRepository.findDriverHistoryForTrip(tripId, passengerId);
	}
	
}

