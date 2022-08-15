package com.mgWork.controllers;

import java.text.ParseException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgWork.entitys.Ticket;
import com.mgWork.service.TicketService;

@RestController
@RequestMapping("/cust")
public class TicketController {

	@Autowired
	private TicketService service;
	

	@PostMapping("/bookticket")
	public ResponseEntity<String> saveTicket(@Valid @RequestBody Ticket ticket) {
		service.saveTicket(ticket);
		return new ResponseEntity<String>("Ticket booking successful tktId : " + ticket.getTktId(), HttpStatus.CREATED);
	}

	@GetMapping("/cancelticket")
	public ResponseEntity<Ticket> cancelTicket(@RequestParam String tktId, Ticket ticket) throws ParseException {
		return new ResponseEntity<Ticket>(service.saveCancelledTicket(tktId, ticket), HttpStatus.CREATED);
	}

	@GetMapping("/showtickets")
	public ResponseEntity<List<Ticket>> showTickets() {
		return new ResponseEntity<List<Ticket>>(service.showTickets(), HttpStatus.OK);

	}

	@GetMapping("/getticket/{id}")
	public ResponseEntity<Ticket> getTicket(@PathVariable String id) {
		return new ResponseEntity<Ticket>(service.getTicket(id), HttpStatus.OK);
	}

}
