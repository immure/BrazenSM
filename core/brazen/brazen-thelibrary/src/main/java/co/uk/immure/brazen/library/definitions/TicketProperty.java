package co.uk.immure.brazen.library.definitions;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

public class TicketProperty {
	
	@Getter @NonNull private TicketType ticketType;
	@Getter @NonNull private String propertyName;
	
	public TicketProperty(TicketType ticketType, String propertyName) {
		this.ticketType = ticketType;
		this.propertyName = propertyName;
		ticketType.addAvailableProperty(this);
	}
	
	@Override
	public String toString() {
		return "TicketProperty [" + ticketType.getName() + "." + propertyName + "]";
	}
	
	

}
