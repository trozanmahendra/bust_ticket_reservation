package com.mgWork.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mgWork.entitys.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	Optional<Ticket> findByTktId(String tkt_id);

	List<Ticket> findByCustomerId(Long customerId);

	Optional<Ticket> deleteByTktId(String tktId);

	
}
