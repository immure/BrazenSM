package uk.co.immure.brazen.services.repositories.mem;

import lombok.extern.log4j.Log4j;
import uk.co.immure.brazen.services.repositories.TicketRepository;
import co.uk.immure.brazen.library.entities.Ticket;

@Log4j
public class TransientTicketRepository extends AbstractTransientRepository<Ticket, String> implements TicketRepository {
	
	private static long lastId = 0;
	private final static TransientTicketRepository instance = new TransientTicketRepository();

	@Override
	public String createUniqueId() {
		return Long.toString(lastId++);
	}

	@Override
	public String getId(Ticket object) {
		return object.getId();
	}
	
	@Override
	public Ticket save(Ticket object) {
		
		if (object.isSkeleton()) {
			// Ticket is skeleton - create new
			// from scratch
			log.debug("Converting skeleton ticket to real");
			Ticket t = Ticket.start(object.getType(), lastId++, object.getInitiator());
			return super.save(t);
		}
		return super.save(object);
		
	}
	
	

}
