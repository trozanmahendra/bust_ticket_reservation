package com.mgWork.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgWork.entitys.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByName(String email);

	Optional<Customer> findByEmail(String name);

	Optional<Customer> findByNameAndPassword(String name, String password);



}
