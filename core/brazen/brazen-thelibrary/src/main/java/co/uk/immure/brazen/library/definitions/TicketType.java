package co.uk.immure.brazen.library.definitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.uk.immure.brazen.library.UserRelationshipType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;

@Data
@Log
public class TicketType {
	
	@NonNull private final String name;
	@NonNull private final String prefix;
    private final List<TicketRelationship> relationships = new ArrayList<TicketRelationship>();
    
    private final List<TicketProperty> availableProperties = new ArrayList<TicketProperty>();
    
    @NonNull private final TicketStage initialStage;
    
    private final List<TicketStage> stages = new ArrayList<TicketStage>();
    
    
    public void addStage(TicketStage stage) {
    	if (!stages.contains(stage)) {
    		stages.add(stage);
    		for (TicketProperty p : stage.getRequiredProperties()) {
    			if (!availableProperties.contains(p)) {
    				log.warning("Stage added a new property - this should be defined in the ticket definition. "
    						+ name + "." + stage.getStageName());
    				addAvailableProperty(p);
    			}
    		}
    		for (TicketProperty p : stage.getEnabledProperties()) {
    			if (!availableProperties.contains(p)) {
    				log.warning("Stage added a new property - this should be defined in the ticket definition. "
    						+ name + "." + stage.getStageName());
    				addAvailableProperty(p);
    			}
    		}
    	} else {
    		log.warning("Attempted to re-add stage " + stage.getStageName() + " to type " + name);
    	}
    }
    
    public void addAvailableProperty(TicketProperty p) {
    	if (!availableProperties.contains(p)) {
    		availableProperties.add(p);
    	}
    }

}
