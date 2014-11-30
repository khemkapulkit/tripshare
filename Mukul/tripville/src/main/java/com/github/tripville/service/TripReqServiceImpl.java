package com.github.tripville.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.tripville.model.Trip;
import com.github.tripville.model.TripReq;
import com.github.tripville.repository.TripRepository;
import com.github.tripville.repository.TripReqRepository;

@Service("tripReqService")
public class TripReqServiceImpl implements TripReqService {

	@Autowired
	private TripReqRepository tripReqRepository;
	
	public TripReq t_req(TripReq t_req) {
		
		return null;
	}


	public ArrayList<TripReq> getTripsforPassenger(String loggedInUserId) {
		return tripReqRepository.getTripsforPassenger(loggedInUserId);
	}


	public ArrayList<TripReq> getTripRequestsForTrip(int tripId) {
		
		return tripReqRepository.getTripRequestsForTrip(tripId);
		
	}


	public TripReq getTripRequestDetails(int tripReqId) {
		System.out.println("~~~~~~~~~~~~ in IMPL" + tripReqId); 
		return tripReqRepository.getTripRequestDetails(tripReqId);
	}

	
	@Transactional
	public TripReq save(TripReq tripReq) {
		return tripReqRepository.save(tripReq);
	}
	
	

}
