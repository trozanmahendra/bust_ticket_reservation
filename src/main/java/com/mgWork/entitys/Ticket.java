package com.mgWork.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tkt_seq")
	@GenericGenerator(name = "tkt_seq", strategy = "com.mgWork.generators.StringPrefixedSequenceIdGenerator", parameters = {
			@Parameter(value = "50", name = "StringPrefixedSequenceIdGenerator.INCREMENT_PARAM"),
			@Parameter(value = "DXC_TKT_Gen_", name = "StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER"),
			@Parameter(value = "%05d", name = "StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER") })
	@Column(unique = true, updatable = false)
	private String tktId;

	@ManyToMany(fetch = FetchType.EAGER)
//	@JoinColumn(name = "psngr_ids")
	private List<Passenger> passengers;

	private Long custId;
	@OneToOne(fetch = FetchType.EAGER)
	private Customer customer;

	private String bus_reg_id;
	@OneToOne(fetch = FetchType.EAGER)
	private Bus bus;
	@Column(nullable = false)
	private String pickUp;

	@Column(nullable = false)
	private String dropp;

	private String status;
	
	private float total_fare;
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Date createdAt;
	@JsonIgnore
	@UpdateTimestamp
	private Date UpdatedAt;

	@Override
	public String toString() {
		return "Ticket [tktId=" + tktId + "" + ", passengers=" + passengers + ", pickUp=" + pickUp + ", dropp=" + dropp
				+ ", status=" + status + "]";
	}

}
