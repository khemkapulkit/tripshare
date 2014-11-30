package com.github.tripville.repository;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.tripville.model.Trip;
import com.github.tripville.model.TripReq;

@Repository("tripReqRepository")
public interface TripReqRepository extends CrudRepository<TripReq, Long> {
	
	@Query("select tr from TripReq tr where tr.copassid = :loggedInUserId")
	ArrayList<TripReq> getTripsforPassenger(@Param("loggedInUserId") String loggedInUserId);
	
	@Query("select t from TripReq t where t.tripid=:tripId")
	ArrayList<TripReq> getTripRequestsForTrip(@Param("tripId") int tripId);
	
	@Query("select t from TripReq t where t.tripreqid=:tripReqId")
	TripReq getTripRequestDetails(@Param("tripReqId") int tripReqId);
}



