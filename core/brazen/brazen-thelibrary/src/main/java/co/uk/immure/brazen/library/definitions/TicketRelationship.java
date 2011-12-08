package co.uk.immure.brazen.library.definitions;

import lombok.Data;
import lombok.NonNull;

@Data
public class TicketRelationship {
	
	public final static int UNLIMITED_CARDINALITY = Integer.MAX_VALUE;
	
	@NonNull private final TicketType nodeA;
	@NonNull private final TicketType nodeB;
	
	@NonNull private final int nodeAMinCardinality;
	@NonNull private final int nodeBMinCardinality;
	
	@NonNull private final int nodeAMaxCardinality;
	@NonNull private final int nodeBMaxCardinality;
	
	private String relationshipName;
	@NonNull private final RelationshipType type;

}
