package com.mgWork.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Passenger implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long id;
	@Column(nullable = false)
	private String psngrName;
	@Column(nullable = false)
	private int psngrAge;
	@Column(nullable = false)
	@JsonIgnore
	private Long customerId;
	@Column(nullable = false)
	private String mobileNumber;

	@Override
	public String toString() {
		return "Passenger [psngr_name=" + psngrName + ", psngr_age=" + psngrAge + "]";
	}

}
