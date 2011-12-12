package co.uk.immure.brazen.services.startup;

import co.uk.immure.brazen.library.definitions.TicketProperty;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.services.repositories.mem.TransientTicketTypeRepository;

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

			TransientTicketTypeRepository.getInstance().save(incidentType);
			initialised = true;
		}

	}

}
