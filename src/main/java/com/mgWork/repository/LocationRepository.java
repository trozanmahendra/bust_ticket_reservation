package com.mgWork.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgWork.entitys.Location;

public interface LocationRepository extends JpaRepository<Location	, Long> {
	
	public Location findByLocation(String location);

}
