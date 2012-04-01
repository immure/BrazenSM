package uk.co.immure.droozen;

import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Ticket {
	
	private String id;
	private String title;
	private String subject;
	private String type;
	private Stage stage;
	private String assignee;
	private List<Stage> onwardStages = Collections.emptyList();
	
	private final PropertyChangeSupport changes = new PropertyChangeSupport(this);
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getSubject() {
		return subject;
	}
	public String getType() {
		return type;
	}
	public Stage getStage() {
		return stage;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public List<Stage> getOnwardStages() {
		return onwardStages;
	}
	public void setOnwardStages(List<Stage> onwardStages) {
		this.onwardStages = onwardStages;
	}
	public void setOnwardStages(Stage[] onwardStages) {
		this.onwardStages = Arrays.asList(onwardStages);
	}

}
