package com.github.tripville.service;

import java.util.ArrayList;
import java.util.List;

import com.github.tripville.model.Trip;
import com.github.tripville.model.TripReq;

public interface TripService {
	Trip save(Trip trip);
	TripReq save(TripReq tripreq);
	String getUserId(String userName);
	Trip getTripDetails(int tripId);
	ArrayList<Trip> getTripsforDriver(String loggedInUserId);
	List<Trip> searchTrip(String startpt, String destinationpt);
}
