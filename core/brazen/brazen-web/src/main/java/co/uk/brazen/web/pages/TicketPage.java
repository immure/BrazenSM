package co.uk.brazen.web.pages;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import co.uk.immure.brazen.library.entities.Ticket;

public class TicketPage implements Page<Ticket> {
	
	public void draw(Panel panel, Ticket data) {
		
		panel.removeAllComponents();
		
		Label id = new Label("<b>ID:</b> " + data.getId());
		Label stage = new Label("<b>Stage:</b>" + data.getCurrentStage().getStageName());
		
		panel.get
		
	}


}
