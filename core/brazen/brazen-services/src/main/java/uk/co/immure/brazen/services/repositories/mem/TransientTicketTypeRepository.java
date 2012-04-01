package uk.co.immure.brazen.services.repositories.mem;

import uk.co.immure.brazen.services.repositories.Repository;
import uk.co.immure.brazen.services.repositories.TicketTypeRepository;
import co.uk.immure.brazen.exceptions.BrazenRuntimeException;
import co.uk.immure.brazen.library.definitions.TicketType;

public class TransientTicketTypeRepository extends AbstractTransientRepository<TicketType, String> implements TicketTypeRepository {
	
	private final static TransientTicketTypeRepository instance = new TransientTicketTypeRepository();

	@Override
	public String getId(TicketType object) {
		return object.getName();
	}

	@Override
	public String createUniqueId() {
		// TODO Auto-generated method stub
		return null;
	}

}
