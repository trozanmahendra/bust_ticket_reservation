package com.mgWork.service;

import java.util.List;

import com.mgWork.entitys.Location;
import com.mgWork.entitys.SubLocation;

public interface SubLocationService {

	SubLocation addSublocation(SubLocation subLocation);

	SubLocation UpdateSublocation(SubLocation subLocation, String location, String sublocation2);

	List<SubLocation> listOfSubLocationsByLocations(Location loc);

}
