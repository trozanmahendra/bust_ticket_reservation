package com.mgWork.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgWork.entitys.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByNameAndPassword(String name, String password);

	Optional<Admin> findByName(String name);

}
