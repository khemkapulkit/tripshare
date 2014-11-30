package com.github.tripville.repository;



import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.tripville.model.DriverHistory;

@Repository("driverRepository")
public interface DriverRepository extends CrudRepository<DriverHistory, Long> { 
	
	@Query("select d from DriverHistory d where d.tripid = :tripId and d.copassId = :passengerId")
	DriverHistory findDriverHistoryForTrip(@Param("tripId") String tripId, @Param("passengerId") String passengerId);
		
}



