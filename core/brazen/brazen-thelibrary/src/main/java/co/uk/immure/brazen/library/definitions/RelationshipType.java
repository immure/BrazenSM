package co.uk.immure.brazen.library.definitions;

public enum RelationshipType {
	
	PARENT_OF, CHILD_OF, RELATED_TO;
	
	public RelationshipType getReverse() {
		switch (this) {
		case PARENT_OF:
			return CHILD_OF;
		case CHILD_OF:
			return PARENT_OF;
		default:
			return RELATED_TO;
		}
	}

}
