package com.github.tripville.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.tripville.model.MemberCar;
import com.github.tripville.model.Trip;
import com.github.tripville.model.TripReq;


@Repository("TripReqRepository")
public interface TripReqRepository extends CrudRepository<TripReq, Long> {

}
