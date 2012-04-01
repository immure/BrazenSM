package uk.co.immure.brazen.cli;

import lombok.extern.java.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.co.immure.brazen.services.repositories.TicketRepository;
import uk.co.immure.brazen.services.repositories.TicketTypeRepository;
import uk.co.immure.brazen.services.startup.Initialiser;


@Log
public class CommandLine {
	
	@Autowired
	protected TicketRepository ticketRepository;
	
	@Autowired
	protected TicketTypeRepository ticketTypeRepository;
	
	@Autowired
	protected Initialiser initialiser;
	
	public CommandLine() {

	}
	
	public void startup() {
		initialiser.setupBrazen();
		log.info(ticketTypeRepository.getAll().toString());
		
		
	
	}

}
