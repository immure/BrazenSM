package co.uk.immure.brazen.library.entities;

import co.uk.immure.brazen.library.definitions.RelationshipType;
import lombok.Data;

@Data
public class Relationship {
	
	private final RelationshipType type;
	private final Ticket otherNode;

}
