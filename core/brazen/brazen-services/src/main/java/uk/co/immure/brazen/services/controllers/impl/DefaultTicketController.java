package uk.co.immure.brazen.services.controllers.impl;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import co.uk.immure.brazen.common.Response;
import co.uk.immure.brazen.exceptions.BrazenException;
import co.uk.immure.brazen.exceptions.BrazenRuntimeException;
import co.uk.immure.brazen.library.definitions.RelationshipType;
import co.uk.immure.brazen.library.definitions.TicketProperty;
import co.uk.immure.brazen.library.definitions.TicketRelationship;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.library.definitions.User;
import co.uk.immure.brazen.library.entities.Relationship;
import co.uk.immure.brazen.library.entities.Ticket;
import co.uk.immure.brazen.library.exceptions.LibraryStructureFailureException;
import co.uk.immure.brazen.library.exceptions.TicketAdvancementException;
import uk.co.immure.brazen.services.controllers.TicketController;
import uk.co.immure.brazen.services.repositories.TicketRepository;
import uk.co.immure.brazen.services.repositories.TicketTypeRepository;

@Controller
@Log4j
public class DefaultTicketController implements TicketController {
	
	private TicketTypeRepository ticketTypeRepository;
	
	private TicketRepository ticketRepository;
	
	public Response<Ticket> createTicketSkeleton(TicketType type, User user) {
		Ticket t = Ticket.start(type, user);
		log.debug(user.getUserID() + " created skeleton ticket " + t);
		return new Response<Ticket>(t);
	};
	
	public Response<Ticket> getTicket(String id) {
		return new Response<Ticket>(getTicketRepository().get(id));
	};
	
	@Override
	public Response<Ticket> saveTicket(Ticket t, User u) {
		
		if (getTicketRepository().get(t.getId()) != null) {
			log.error(u.getUserID() + " tried to save non-skeleton ticket " + t);
			throw new BrazenRuntimeException("Cannot save non-skeleton ticket");
		}
		log.debug(u.getUserID() + " saved new ticket " + t);
		return new Response<Ticket>(getTicketRepository().save(t));
		
	}
	
	@Override
	public Response<Ticket> addRelationship(String id1, String id2,
			RelationshipType rt) {
		
		if (id1.equals(id2)) {
			log.error("Attempted to relate ticket to itself: " + id1);
			return new Response<Ticket>(new BrazenException("Tickets cannot be related to themselves"));
		}
		
		Response<Ticket> tr1 = getTicket(id1);
		Response<Ticket> tr2 = getTicket(id2);
		
		if (tr1.hasError()) {
			return tr1;
		} 
		if (tr2.hasError()) {
			return tr2;
		}
		
		Ticket t1 = tr1.getPayload();
		Ticket t2 = tr2.getPayload();
				
		
		if (!t1.isRelatedTo(t2) && !t2.isRelatedTo(t1)) {
			t1.getRelationships().add(new Relationship(rt, t2));
			t2.getRelationships().add(new Relationship(rt.getReverse(), t1));
			t1 = getTicketRepository().save(t1);
			t2 = getTicketRepository().save(t2);
			return new Response<Ticket>(t1);
		} else {
			return new Response<Ticket>(new BrazenException("Tickets " + t1 + " and " + t2 + " are already related."));
		}
		
	}
	
	@Override
	public Response<Ticket> setProperty(String id, TicketProperty property, String value) {
		Ticket t = getTicketRepository().get(id);
		if (t.getProperties().containsKey(property)) {
			log.debug("Overwriting property: " + property.getPropertyName() + " in ticket " + id);
		}
		t.setProperty(property, value);
		t = getTicketRepository().save(t);
		return new Response<Ticket>(t);
	}

	public TicketTypeRepository getTicketTypeRepository() {
		return ticketTypeRepository;
	}

	@Autowired
	public void setTicketTypeRepository(TicketTypeRepository ticketTypeRepository) {
		this.ticketTypeRepository = ticketTypeRepository;
	}

	public TicketRepository getTicketRepository() {
		return ticketRepository;
	}

	@Autowired
	public void setTicketRepository(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	@Override
	public Response<Ticket> updateStatus(String id, TicketStage ts) {
		Response<Ticket> tr1 = getTicket(id);
		if (tr1.hasError()) return tr1;
		Ticket t = tr1.getPayload();
		
		if (t.getCurrentStage().getNextStages().contains(ts)) {

			if (t.getMissingProperties(ts).size() == 0) {
				log.debug("Advancing " + t.getId() + " from stage "
						+ t.getCurrentStage().getStageName() + "to"
						+ ts.getStageName());
				t.setCurrentStage(ts);
				ticketRepository.save(t);
				return new Response<Ticket>(t);
				
			} else {
				return new Response<Ticket>(new TicketAdvancementException("Could not advance "
						+ t.getId() + " due to missing properties: "
						+ t.getMissingProperties(ts).toString()));
			}
		} else {
			return new Response<Ticket>(new LibraryStructureFailureException(ts.getStageName()
					+ " is not a valid next stage for " + t.getId() + " ("
					+ t.getType().getName() + "." + t.getCurrentStage().getStageName()
					+ ")"));
		}
	}
	

}
