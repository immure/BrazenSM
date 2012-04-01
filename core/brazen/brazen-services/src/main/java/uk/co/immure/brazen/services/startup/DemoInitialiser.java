package uk.co.immure.brazen.services.startup;

import org.springframework.beans.factory.annotation.Autowired;

import uk.co.immure.brazen.services.repositories.mem.TransientTicketRepository;
import uk.co.immure.brazen.services.repositories.mem.TransientTicketTypeRepository;

import co.uk.immure.brazen.library.definitions.TicketProperty;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;

public class DemoInitialiser implements Initialiser {

	private static TicketType incidentType;
	private static TicketStage registered;
	private static TicketStage accepted;
	private static TicketStage inProgress;
	private static TicketStage waiting;
	private static TicketStage resolved;
	private static TicketStage closed;
	private static TicketProperty solutionProperty;

	private static boolean initialised = false;
	
	@Autowired
	protected TransientTicketTypeRepository transientTicketTypeRepository;

	@Override
	public boolean isInitialised() {
		// TODO Auto-generated method stub
		return initialised;
	}

	@Override
	public synchronized void setupBrazen() {
		// Create stages
		if (!isInitialised()) {

			registered = new TicketStage("Registered");
			accepted = new TicketStage("Accepted");
			inProgress = new TicketStage("In Progress");
			waiting = new TicketStage("Waiting");
			resolved = new TicketStage("Resolved");
			closed = new TicketStage("Closed");

			// Set workflow

			registered.addNextStage(accepted);

			accepted.addNextStage(resolved);
			accepted.addNextStage(inProgress);
			accepted.addNextStage(waiting);

			inProgress.addNextStage(waiting);
			inProgress.addNextStage(accepted);
			inProgress.addNextStage(resolved);

			waiting.addNextStage(resolved);
			waiting.addNextStage(accepted);
			waiting.addNextStage(inProgress);

			resolved.addNextStage(accepted);
			resolved.addNextStage(closed);

			// Create Ticket Type

			incidentType = new TicketType("Incident", "IN", registered);

			// Create properties

			solutionProperty = new TicketProperty(incidentType, "Solution");

			// Add required properties

			resolved.addRequiredProperty(solutionProperty);

			transientTicketTypeRepository.save(incidentType);
			initialised = true;
		}

	}

}
