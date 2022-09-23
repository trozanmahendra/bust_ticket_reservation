package com.mgWork.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "^((?=[A-Za-z0-9])(?![_\\-@]).)*$", message = "name should not contain any special symbols")
	private String name;
	@Column(nullable = false, unique = true)
	@Email(message = "must include @ while writing mail Address")
	private String email;

	@Column(nullable = false)
	@Size(min = 4, message = "Password must be 4 char long")
	@JsonIgnore
	private String password;

	private int age;

	@JsonIgnore
	private String adminCode;

	private String customerMobileNumber;

	@CreationTimestamp
	@JsonIgnore
	@Column(nullable = false, updatable = false)
	private Date createdAt;
	@JsonIgnore
	@UpdateTimestamp
	private Date UpdatedAt;
	@OneToOne
	@JoinColumn(name = "authority_id")
	@JsonIgnore
	private Authority authority;

	@Override
	public String toString() {
		return "Customer [name=" + name + ", email=" + email + ", age=" + age + "]";
	}

	

}
