/**
 * 
 */
package co.uk.immure.brazen.library.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.immure.brazen.exceptions.BrazenException;
import co.uk.immure.brazen.exceptions.BrazenRuntimeException;
import co.uk.immure.brazen.library.definitions.RelationshipType;
import co.uk.immure.brazen.library.definitions.TicketProperty;
import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.library.definitions.User;
import co.uk.immure.brazen.library.exceptions.LibraryStructureFailureException;
import co.uk.immure.brazen.library.exceptions.TicketAdvancementException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.extern.java.Log;

/**
 * @author mike
 * 
 */

@Log
public class Ticket implements Serializable {

	@Getter
	private final TicketType type;
	@Getter
	private final long uniqueId;
	@Getter
	@Setter
	private TicketStage currentStage;

	@Getter
	private final User initiator;

	private final Map<TicketProperty, String> properties = new HashMap<TicketProperty, String>();

	private final List<Relationship> relationships = new ArrayList<Relationship>();

	protected Ticket(TicketType type, long id, User initiator) {
		this.type = type;
		this.uniqueId = id;
		this.initiator = initiator;
	}

	Ticket(TicketType type, long id, TicketStage stage, User initiator) {
		this.type = type;
		this.uniqueId = id;
		this.initiator = initiator;
	}

	public static Ticket start(TicketType type, User initiator) {
		Ticket ticket = new Ticket(type, -1, initiator);
		ticket.start();
		return ticket;
	}
	
	public static Ticket start(TicketType type, long id, User initiator) {
		Ticket ticket = new Ticket(type, id, initiator);
		ticket.start();
		return ticket;
	}

	protected void start() {
		setCurrentStage(getType().getInitialStage());
	}

	public String getId() {
		return getType().getPrefix() + getUniqueId();
	}

	public List<TicketStage> nextStages() {
		return getCurrentStage().getNextStages();
	}

	public void advanceStage(TicketStage stage)
			throws TicketAdvancementException {
		log.entering(Ticket.class.getName(), "advanceStage");
		if (getCurrentStage().getNextStages().contains(stage)) {

			if (getMissingProperties(stage).size() == 0) {
				log.fine("Advancing " + getId() + " from stage "
						+ getCurrentStage().getStageName() + "to"
						+ stage.getStageName());
				setCurrentStage(stage);
			} else {
				throw new TicketAdvancementException("Could not advance "
						+ getId() + " due to missing properties: "
						+ getMissingProperties(stage).toString());
			}
		} else {
			throw new LibraryStructureFailureException(stage.getStageName()
					+ " is not a valid next stage for " + getId() + " ("
					+ type.getName() + "." + getCurrentStage().getStageName()
					+ ")");
		}
	}

	public Collection<TicketProperty> getMissingProperties(TicketStage stage) {
		List<TicketProperty> missingProperties = new ArrayList<TicketProperty>(
				stage.getRequiredProperties());
		for (TicketProperty property : getProperties().keySet()) {
			missingProperties.remove(property);
		}
		return Collections.unmodifiableCollection(missingProperties);
	}

	public void setProperty(TicketProperty property, String text) {
		if (getType().getAvailableProperties().contains(property)) {
			getProperties().put(property, text);
		} else {
			throw new LibraryStructureFailureException(
					"Attempted to set non-existant property: " + getType()
							+ "." + property.getPropertyName());
		}
	}

	public boolean isRelatedTo(Ticket ticket) {
		for (Relationship r : getRelationships()) {
			if (r.getOtherNode().equals(ticket)) {
				return true;
			}
		}
		return false;
	}

	public RelationshipType getRelationTo(Ticket ticket) {
		for (Relationship r : getRelationships()) {
			if (r.getOtherNode().equals(ticket)) {
				return r.getType();
			}
		}
		return null;
	}

//	public boolean addRelationship(RelationshipType relationshipType,
//			Ticket otherNode) {
//		if (otherNode.equals(this)) {
//			throw new BrazenRuntimeException("Cannot relate a ticket to itself");
//		}
//		boolean relationshipAdded = false;
//		if (!isRelatedTo(otherNode)) {
//			getRelationships().add(new Relationship(relationshipType, otherNode));
//			if (!otherNode.isRelatedTo(this)) {
//				otherNode.addRelationship(relationshipType.getReverse(), this);
//			}
//			relationshipAdded = true;
//		}
//		return relationshipAdded;
//
//	}
	
	public boolean isSkeleton() {
		return uniqueId == -1;
	}

	@Override
	public String toString() {
		return "Ticket [" + getType().getName() + ", " + getId() + "]";
	}

	public List<Relationship> getRelationships() {
		return relationships;
	}

	public Map<TicketProperty, String> getProperties() {
		return Collections.unmodifiableMap(properties);
	}

}
