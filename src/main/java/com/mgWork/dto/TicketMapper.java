package com.mgWork.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.mgWork.entitys.Passenger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@Entity
public class TicketMapper {

//	Bus details
	private String bus_name;
	private String regId;
	private String bus_type;
	private String origin;
	private String destination;
	private float tkt_fare;
	private Date start_date;
	private Date end_date;

//	Customer details
	private String name;
	private String email;
	private int age;
	private String customerMobileNumber;
//	Ticket details
	private String tktId;
	private Long custId;
	private Long bus_id;
	
	private String pickUp;
	private String dropp;
	private String status;
//	Passengers
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Passenger> passengers;
}
