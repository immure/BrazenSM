package co.uk.immure.brazen.services.repositories.mem;

import co.uk.immure.brazen.library.entities.Ticket;

public class TransientTicketRepository extends AbstractTransientRepository<Ticket, String> {
	
	private static int lastId = 0;
	private final static TransientTicketRepository instance = new TransientTicketRepository();

	@Override
	public String createUniqueId() {
		return Integer.toString(lastId++);
	}

	@Override
	public String getId(Ticket object) {
		return object.getId();
	}

	public static TransientTicketRepository getInstance() {
		return instance;
	}

}
