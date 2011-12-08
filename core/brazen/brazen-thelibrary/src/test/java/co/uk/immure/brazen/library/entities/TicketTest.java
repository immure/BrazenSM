package co.uk.immure.brazen.library.entities;

import static org.junit.Assert.*;

import org.junit.Test;

import co.uk.immure.brazen.library.definitions.RelationshipType;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;  
import co.uk.immure.brazen.library.definitions.User;

public class TicketTest {
	
	protected TicketType createTestTicketType() {
		TicketStage ts = new TicketStage("TestStage1");
		TicketType t = new TicketType("test", "TE", ts);
		ts.setStageType(t);
		return t;
		
	}
	
	@Test
	public void testGetId() {
		TicketType tt = createTestTicketType();
		Ticket t = Ticket.start(tt, 123456789, new User("test"));
		assertEquals("ID not as expected", t.getId(), "TE123456789");
	}
	
	@Test
	public void testRelationshipAdd() {
		TicketType tt = createTestTicketType();
		Ticket t1 = Ticket.start(tt, 12345, new User("test"));
		Ticket t2 = Ticket.start(tt, 12346, new User("test"));
		
		t1.addRelationship(RelationshipType.RELATED_TO, t2);
		
		assertTrue(t1.isRelatedTo(t2));
		assertTrue(t2.isRelatedTo(t1));
		assertFalse(t1.isRelatedTo(t1));
		assertFalse(t2.isRelatedTo(t2));
		assertEquals(t1.getRelationTo(t2), RelationshipType.RELATED_TO);
		assertEquals(t2.getRelationTo(t1), RelationshipType.RELATED_TO);
	}
	
	public void testRelationshipAddParent() {
		TicketType tt = createTestTicketType();
		Ticket t1 = Ticket.start(tt, 12345, new User("test"));
		Ticket t2 = Ticket.start(tt, 12346, new User("test"));
		
		t1.addRelationship(RelationshipType.PARENT_OF, t2);
		
		assertEquals(t1.getRelationTo(t2), RelationshipType.PARENT_OF);
		assertEquals(t2.getRelationTo(t1), RelationshipType.CHILD_OF);
	}

}
