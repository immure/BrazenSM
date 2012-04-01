package co.uk.immure.brazen.services.controllers.impl;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import co.uk.immure.brazen.common.Response;
import co.uk.immure.brazen.library.definitions.RelationshipType;
import co.uk.immure.brazen.library.definitions.TicketProperty;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.library.definitions.User;
import co.uk.immure.brazen.library.entities.Ticket;

import uk.co.immure.brazen.services.controllers.TicketController;
import uk.co.immure.brazen.services.controllers.impl.DefaultTicketController;
import uk.co.immure.brazen.services.repositories.TicketRepository;
import uk.co.immure.brazen.services.repositories.TicketTypeRepository;
import uk.co.immure.brazen.services.repositories.mem.TransientTicketRepository;
import uk.co.immure.brazen.services.repositories.mem.TransientTicketTypeRepository;

public class DefaultTicketControllerTest {
	
	private DefaultTicketController controller;

	@Before
	public void setUp() {
		
		controller = new DefaultTicketController();
		TicketRepository tr = new TransientTicketRepository();
		TicketTypeRepository ttr = new TransientTicketTypeRepository();
		controller.setTicketRepository(tr);
		controller.setTicketTypeRepository(ttr);
		
	}
	
	@Test
	public void testCreateTestSkeleton() {
		User u = new User("test");
		Response<Ticket> tr = controller.createTicketSkeleton(incidentType, u);
		assertFalse(tr.hasError());
		Ticket t = tr.getPayload();
		assertEquals(-1, t.getUniqueId());
	}
	
	@Test
	public void testSave() {
		User u = new User("test");
		Response<Ticket> tr = controller.createTicketSkeleton(incidentType, u);
		assertFalse(tr.hasError());
		Ticket t = tr.getPayload();
		assertEquals(-1, t.getUniqueId());
		tr = controller.saveTicket(t, u);
		assertFalse(tr.hasError());
		t = tr.getPayload();
		assertFalse(-1 == t.getUniqueId());
		
	}
	
	@Test
	public void testAddRelationship() {
		
		User u = new User("test");
		Response<Ticket> tr = controller.createTicketSkeleton(incidentType, u);
		assertFalse(tr.hasError());
		Ticket t = tr.getPayload();
		t = controller.saveTicket(t, u).getPayload();
		
		Response<Ticket> tr2 = controller.createTicketSkeleton(incidentType, u);
		assertFalse(tr2.hasError());
		Ticket t2 = tr2.getPayload();
		t2 = controller.saveTicket(t2, u).getPayload();
		
		Response<Ticket> relationshipResponse = 
				controller.addRelationship(t.getId(), t2.getId(), RelationshipType.PARENT_OF);
		
		assertResponseSuccess(relationshipResponse);
		
		tr = controller.getTicket(t.getId());
		tr2 = controller.getTicket(t2.getId());
		
		t = tr.getPayload();
		t2 = tr2.getPayload();
		
		assertTrue(t.isRelatedTo(t2));
		assertTrue(t2.isRelatedTo(t));
		
		assertEquals(t.getRelationTo(t2), RelationshipType.PARENT_OF);
		assertEquals(t2.getRelationTo(t), RelationshipType.CHILD_OF);
		
	}
	
	protected void assertResponseSuccess(Response<? extends Serializable> response) {
		if (response.hasError()) {
			fail(response.getError().toString());
		}
	}
	
	
	
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
	

}
