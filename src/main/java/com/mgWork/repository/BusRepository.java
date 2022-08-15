package com.mgWork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mgWork.entitys.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {
	
	List<Bus> findByOriginAndDestination(String origin,String destination,Pageable pageable);

	Optional<Bus> findByRegId(String regId);

}
