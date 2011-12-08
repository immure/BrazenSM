package co.uk.immure.brazen.library.definitions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Delegate;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;

@Data
@Log
public class TicketStage {
	
	
	@NonNull private final String stageName;
	
	protected TicketType stageType;
	
	/**
	 *   Properties in this list are required before this stage can be entered.
	 *   This property must also be present in the TicketProperty master
	 *   properties list to be correct 
	 */
	private final List<TicketProperty> requiredProperties = new ArrayList<TicketProperty>();
	
	/**
	 *   Properties in this list are enabled at this stage, but are not compulsory
	 *   This property must also be present in the TicketProperty master
	 *   properties list to be correct 
	 */
	private final List<TicketProperty> enabledProperties = new ArrayList<TicketProperty>();
	
	@Getter(value=AccessLevel.NONE) private final List<TicketStage> nextStages = new ArrayList<TicketStage>();
	
	public void addNextStage(@NonNull TicketStage stage) {
		if (!getNextStages().contains(stage)) {
			nextStages.add(stage);
		} else {
			log.warning("Attempted to add next stage " + stage.getStageName() + " to " + 
					stageType + "." + stageName + ", but it already exists");
		}
	}
	
	public List<TicketStage> getNextStages() {
		return Collections.unmodifiableList(nextStages);
	}
	
	public void addRequiredProperty(TicketProperty property) {
		requiredProperties.add(property);
	}
	
	@Override
	public String toString() {
		return "TicketStage [" + getStageType() + "." + getStageName() + "]";
	}

}
