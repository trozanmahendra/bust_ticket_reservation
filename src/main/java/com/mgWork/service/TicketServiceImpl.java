package com.mgWork.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mgWork.entitys.Bus;
import com.mgWork.entitys.Customer;
import com.mgWork.entitys.Location;
import com.mgWork.entitys.Passenger;
import com.mgWork.entitys.SubLocation;
import com.mgWork.entitys.Ticket;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.BusRepository;
import com.mgWork.repository.CustomerRepository;
import com.mgWork.repository.LocationRepository;
import com.mgWork.repository.PassengerRepository;
import com.mgWork.repository.SubLocationRepository;
import com.mgWork.repository.TicketRepository;

@Service
@Transactional
public class TicketServiceImpl implements TicketService {

	private TicketRepository ticketRepo;

	private BusRepository busRepository;

	private CustomerRepository customerRepository;

	private PassengerRepository passengerRepository;

	private CustomerService customerService;

	private LocationRepository locationRepository;

	private SubLocationRepository subLocationRepository;

	private Date date;

	public TicketServiceImpl(TicketRepository ticketRepo, BusRepository busRepository,
			CustomerRepository customerRepository, PassengerRepository passengerRepository,
			CustomerService customerService, LocationRepository locationRepository,
			SubLocationRepository subLocationRepository, Date date) {
		super();
		this.ticketRepo = ticketRepo;
		this.busRepository = busRepository;
		this.customerRepository = customerRepository;
		this.passengerRepository = passengerRepository;
		this.customerService = customerService;
		this.locationRepository = locationRepository;
		this.subLocationRepository = subLocationRepository;
		this.date = date;
	}

	@Override
	public Ticket saveTicket(Ticket ticket) {
		MgLogger.logAudit("com.mgWork.service.TicketServiceImpl.saveTicket(Ticket) method invoked");
		Customer customer = customerService.getLoggedInCustomer();

		ticket.setCustId(customer.getId());
		ticket.setCustomer(customerRepository.findById(customer.getId()).get());

		Bus bus = busRepository.findByRegId(ticket.getBus_reg_id()).get();
		ticket.setBus(bus);

		String origin = bus.getOrigin();
		String pickUp = ticket.getPickUp();
		String Destination = bus.getDestination();
		String drop = ticket.getDropp();

		Location loc1 = locationRepository.findByLocation(origin);

		SubLocation loc2 = subLocationRepository.findBySubLoc(pickUp);
		Location loc3 = locationRepository.findByLocation(Destination);
		SubLocation loc4 = subLocationRepository.findBySubLoc(drop);

		List<Passenger> pids = ticket.getPassengers();
		Passenger passenger = null;
		for (int i = 0; i < pids.size(); i++) {
			passenger = passengerRepository.findByCustomerIdAndId(customer.getId(),
					ticket.getPassengers().get(i).getId());

		}
		if (passenger != null) {
			if (loc1 == loc2.getLoc() && loc3 == loc4.getLoc() && loc1 != loc3) {
				int seatsAvail = bus.getSeatsAvailable() - pids.size();
				if (seatsAvail >= 0) {
					Bus buss = busRepository.findById(bus.getBus_id()).get();
					buss.setSeatsAvailable(seatsAvail);
					try {
						busRepository.save(buss);
					} catch (Exception e) {
						e.printStackTrace();
					}
					ticket.setStatus("active");
					float tkt_fare = buss.getTkt_fare();
					ticket.setTotal_fare(tkt_fare * pids.size());
//					System.out.println(ticket+"-------------------");
					return ticketRepo.save(ticket);
				} else
					throw new RuntimeException(" No seats available");
			} else
				throw new RuntimeException(" pickUp or drop point errors");
		} else {
			throw new RuntimeException("Passenger details are not valid ");
		}

	}

	@Override
	public Ticket saveCancelledTicket(String tktId, Ticket ticket) throws ParseException {
		MgLogger.logAudit("com.mgWork.service.TicketServiceImpl.saveCancelledTicket(String, Ticket) method invoked");
		ticket = ticketRepo.findByTktId(tktId).get();
		ticket.setStatus("cancelled");
		Bus bus = busRepository.findByRegId(ticket.getBus_reg_id()).get();
		int seatsAvail = bus.getSeatsAvailable();
		bus.setSeatsAvailable(seatsAvail + ticket.getPassengers().size());
		busRepository.save(bus);

		Date firstDate = bus.getStart_date();
		Date secondDate = date;

		long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
		long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		if (diff < 5)
			throw new RuntimeException(
					"Ticket can't be cancelled,cancellation time is up for this ticket : " + ticket.getTktId());
		else
			return ticketRepo.save(ticket);
	}

	@Override
	public List<Ticket> showTickets() {
		MgLogger.logAudit("com.mgWork.service.TicketServiceImpl.showTickets() method invoked");
		Customer customer = customerService.getLoggedInCustomer();

		List<Ticket> tickets = ticketRepo.findByCustomerId(customer.getId());
		Date firstDate = null;

		Date secondDate = date;

		for (int i = 0; i < tickets.size(); i++) {
			boolean b1 = tickets.get(i).getStatus().equalsIgnoreCase("active");
			Bus bus = busRepository.findByRegId(tickets.get(i).getBus_reg_id()).get();
			firstDate = bus.getEnd_date();
			long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			if (diff <= 0 && b1)
				tickets.get(i).setStatus("expired");
		}

		return ticketRepo.findByCustomerId(customer.getId());
	}

	@Override
	public Ticket getTicket(String id) {
		MgLogger.logAudit("com.mgWork.service.TicketServiceImpl.getTicket(String) method invoked");
		Customer customer = customerService.getLoggedInCustomer();
		if (customer.getId() == ticketRepo.findByTktId(id).get().getCustId())
			return ticketRepo.findByTktId(id).get();
		else
			throw new RuntimeException("Invalid ticket :" + id);
	}

}
