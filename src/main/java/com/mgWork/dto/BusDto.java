package com.mgWork.dto;

import java.util.Date;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusDto {

	private String bus_name;
	private String regId;
	private String bus_type;
	private int seats;
	private int seatsAvailable;
	private String origin;
	private String destination;
	private float tkt_fare;
	private Date start_date;
	private Date end_date;
}
