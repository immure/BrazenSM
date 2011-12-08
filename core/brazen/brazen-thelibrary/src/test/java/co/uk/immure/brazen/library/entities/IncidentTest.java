package co.uk.immure.brazen.library.entities;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import co.uk.immure.brazen.library.definitions.TicketProperty;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.library.definitions.User;
import co.uk.immure.brazen.library.exceptions.TicketAdvancementException;

public class IncidentTest {
	
	private static TicketType incidentType;
	private static TicketStage registered;
	private static TicketStage accepted;
	private static TicketStage inProgress;
	private static TicketStage waiting;
	private static TicketStage resolved;
	private static TicketStage closed;
	private static TicketProperty solutionProperty;
	
	@BeforeClass
	public static void createIncidentType() {
		
		// Create stages
		
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
	}
	
	
	@Test
	public void testIncidentWorkflow() throws TicketAdvancementException {
		Ticket incident = Ticket.start(incidentType, 0, new User("test"));
		assertEquals(incident.getCurrentStage(), registered);
		
		incident.advanceStage(accepted);
		assertEquals(incident.getCurrentStage(), accepted);
		
		incident.setProperty(solutionProperty, "Test123");
		
		incident.advanceStage(resolved);
		assertEquals(incident.getCurrentStage(), resolved);
		
		incident.advanceStage(closed);
		assertEquals(incident.getCurrentStage(), closed);
	}
	
	@Test(expected=TicketAdvancementException.class)
	public void testPropertyException() throws TicketAdvancementException {
		Ticket incident = Ticket.start(incidentType, 0, new User("test"));
		assertEquals(incident.getCurrentStage(), registered);
		
		incident.advanceStage(accepted);
		assertEquals(incident.getCurrentStage(), accepted);
		
		incident.advanceStage(resolved);
	}
	
	
	

}
