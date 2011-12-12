package co.uk.immure.brazen.services.repositories.mem;

import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.services.repositories.Repository;

public class TransientTicketTypeRepository extends AbstractTransientRepository<TicketType, String> implements Repository<TicketType, String> {
	
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

	public static TransientTicketTypeRepository getInstance() {
		return instance;
	}

}
