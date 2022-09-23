package com.mgWork;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.mgWork.entitys.Bus;
import com.mgWork.entitys.Ticket;
import com.mgWork.logger.MgLogger;
import com.mgWork.repository.BusRepository;
import com.mgWork.repository.TicketRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition
public class TicketReservationSystemApplication {
	@Autowired

	public static void main(String[] args) {
		SpringApplication.run(TicketReservationSystemApplication.class, args);
		MgLogger.logAudit("+++++++++++++++++++++++++++++++TicketReservationSystemApplication has started");

	}

	@Autowired
	private TicketRepository ticketRepo;
	@Autowired
	private BusRepository busRepository;
	@Autowired
	private Date date;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {

	}

	@Scheduled(fixedDelay = 60 * 60 * 1000l)
	public void ticketHourlyStatusUpdate() {
		MgLogger.logAudit("+++++++++++++++++++++++++++++++ticketHourlyStatusUpdate has started");

		MgLogger.logAudit("-----------------------ticketStatus updating-----------------------");
		List<Ticket> tickets = ticketRepo.findAll();
		Date firstDate = null;

		Date secondDate = date;

		for (int i = 0; i < tickets.size(); i++) {
			boolean isActive = tickets.get(i).getStatus().equalsIgnoreCase("active");
			boolean iscancelled = tickets.get(i).getStatus().equalsIgnoreCase("cancelled");
			Bus bus = busRepository.findByRegId(tickets.get(i).getBus_reg_id()).get();
			firstDate = bus.getEnd_date();
			long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//			System.out.println("-------*****" + secondDate.getTime() + "========" + firstDate.getTime() + "=====" + diff
//					+ "---------------");
			if (diff <= 0 && isActive) {
				System.out.println("ticketHourlyStatusUpdate invoked");
				tickets.get(i).setStatus("expired");
				ticketRepo.save(tickets.get(i));

			}
//			while explaining makr diff=0
			if (diff <= 7 && iscancelled) {
				System.out.println("deleted tickets which are expired and cancelled older than 7 days");
			ticketRepo.delete(tickets.get(i));
			}
		}
	}

	@Scheduled(fixedDelay =  24*60 * 60 * 1000l)
	public void busDateUpdations() {
		List<Bus> buses = busRepository.findAll();
		Date firstDate = null;

		Date secondDate = date;

		for (int i = 0; i < buses.size(); i++) {
			Bus bus = buses.get(i);
			firstDate = bus.getEnd_date();
			long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
			long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//			System.out.println("---------"+diff);
			if (diff <= 24) {
				System.out.println("Buses dates updated for today :" + date);
				bus.setStart_date(new Date(firstDate.getTime() + (1000 * 60 * 60 * 24)));
				bus.setEnd_date(new Date(firstDate.getTime() + (1000 * 60 * 60 * 24) + (1000 * 60 * 60 * 6)));
				bus.setSeatsAvailable(bus.getSeats());
				busRepository.save(bus);
			}

		}
	}

}
