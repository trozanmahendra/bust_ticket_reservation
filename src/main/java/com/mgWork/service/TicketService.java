package com.mgWork.service;

import java.text.ParseException;
import java.util.List;

import com.mgWork.entitys.Ticket;

public interface TicketService {

	Ticket saveTicket(Ticket ticket);

	List<Ticket> showTickets();

	Ticket getTicket(String id);

	Ticket saveCancelledTicket(String tktId, Ticket ticket) throws ParseException;

}
