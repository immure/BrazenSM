package uk.co.immure.brazen.services.controllers;

import java.util.Collection;

import org.springframework.stereotype.Controller;

import co.uk.immure.brazen.common.Response;
import co.uk.immure.brazen.library.definitions.RelationshipType;
import co.uk.immure.brazen.library.definitions.TicketProperty;
import co.uk.immure.brazen.library.definitions.TicketRelationship;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.library.definitions.User;
import co.uk.immure.brazen.library.entities.Ticket;

public interface TicketController {
	
	public Response<Ticket> getTicket(String ID);
	
	public Response<Ticket> createTicketSkeleton(TicketType type, User u);
	
	public Response<Ticket> saveTicket(Ticket t, User u);
	
	public Response<Ticket> updateStatus(String id, TicketStage ts);
	
	public Response<Ticket> addRelationship(String id1, String id2, RelationshipType tr);
	
	public Response<Ticket> setProperty(String id, TicketProperty property, String value);

}
