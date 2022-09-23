package com.mgWork.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Location {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(updatable = true,nullable = false,unique = true)
	private String location;

	
	
//	@OneToMany(cascade = CascadeType.ALL)
//	private List<SubLocation> subLocs = new ArrayList<SubLocation>();
	
}
