package com.mgWork.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Future;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@ToString
public class Bus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long bus_id;

	@Column(nullable = false)
	private String bus_name;

	@Column(unique = true, nullable = false)
	private String regId;

	private String bus_type;
	private int seats;

//	@JsonIgnore
	private int seatsAvailable;
	private String origin;
	private String destination;
	private float tkt_fare;

	@Future(message = "start_date must be a future date only")
	private Date start_date;
	@Future(message = "end_date must be a future date only")
	private Date end_date;

	@CreationTimestamp
	@JsonIgnore
	@Column(nullable = false, updatable = false)
	private Date createdAt;
	@JsonIgnore
	@UpdateTimestamp
	private Date UpdatedAt;

	@Override
	public String toString() {
		return "Bus [bus_name=" + bus_name + ", regId=" + regId + ", bus_type=" + bus_type + ", seats=" + seats
				+ ", origin=" + origin + ", pickup_point=" + ", destination=" + destination + ", drop_point="
				+ ", tkt_fare=" + tkt_fare + ", start_date=" + start_date + ", end_date=" + end_date + "]";
	}

}
