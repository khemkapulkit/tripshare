package com.github.tripville.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.github.tripville.model.Member;
import com.github.tripville.model.Trip;
import com.github.tripville.model.TripReq;

@Repository("tripRepository")
public interface TripRepository extends CrudRepository<Trip, Long>  {//extends JpaRepository<Member, Long> {
	
	@Query("select t from Trip t where t.fromAddress = :startpt and  t.toAddress  = :destinationpt")
	List<Trip> searchTrip(@Param("startpt") String startpt, @Param("destinationpt") String destinationpt);
	
	@Query("select m from Member m where m.userName = :userName")
	Member getUserInfo(@Param("userName") String userName);
	
}


