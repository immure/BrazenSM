package uk.co.immure.droozen;

public class Incident extends Ticket {
	
	public static enum STAGES implements Stage {
		NEW, INITIALISED, ASSIGNED, PENDING, RESOLVED, CLOSED;
	}

}
