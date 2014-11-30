package com.github.tripville.service;
import java.util.ArrayList;

import com.github.tripville.model.Trip;
import com.github.tripville.model.TripReq;


public interface TripReqService {

	//TripReq t_req(TripReq t_req);
	
	TripReq save(TripReq tripReq);
	
	ArrayList<TripReq> getTripsforPassenger(String loggedInUserId);//passenger id as userid
	
	ArrayList<TripReq> getTripRequestsForTrip(int tripId);
	
	TripReq getTripRequestDetails(int tripReqId);
}
