package co.uk.immure.brazen.services;

import org.junit.Test;
import static org.junit.Assert.*;

import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.library.definitions.User;
import co.uk.immure.brazen.library.entities.Ticket;
import co.uk.immure.brazen.services.repositories.Repository;
import co.uk.immure.brazen.services.repositories.mem.TransientTicketRepository;


public class TransientTicketRepositoryTest {
	
	private final static Repository<Ticket, String> ticketRepository = new TransientTicketRepository();
	
	protected TicketType createTestTicketType() {
		TicketStage ts = new TicketStage("TestStage1");
		TicketType t = new TicketType("test", "TE", ts);
		ts.setStageType(t);
		return t;
		
	}

	@Test
	public void saveAndRetrieve() {
		
		// Save 
		
		TicketType tt = createTestTicketType();
		long id = Long.parseLong(ticketRepository.createUniqueId());
		Ticket t = Ticket.start(tt, id, new User("test"));
		String uniqueId = t.getId();
		ticketRepository.save(t);
		
		// Load
		
		Ticket t2 = ticketRepository.get(uniqueId);
		assertNotNull(t2);
				
	}
	
}
