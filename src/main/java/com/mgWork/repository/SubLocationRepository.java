package com.mgWork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgWork.entitys.Location;
import com.mgWork.entitys.SubLocation;

public interface SubLocationRepository  extends JpaRepository<SubLocation, Long>{

	SubLocation findBySubLoc(String pickUp);

//	Optional<SubLocation> findByLoc(Location loc);

	Optional<SubLocation>  findByLocAndSubLoc(Location loc, String sublocation);

	Optional<List<SubLocation>> findByLoc(Location location);

}
