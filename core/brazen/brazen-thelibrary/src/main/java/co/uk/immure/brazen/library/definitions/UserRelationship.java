package co.uk.immure.brazen.library.definitions;

import lombok.Data;
import co.uk.immure.brazen.library.UserRelationshipType;

@Data
public class UserRelationship {
	
	private final User user;
	private final UserRelationshipType type;

}
