/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package co.uk.brazen.web;

import java.util.Collection;

import lombok.extern.java.Log;

import co.uk.immure.brazen.library.definitions.TicketStage;
import co.uk.immure.brazen.library.definitions.TicketType;
import co.uk.immure.brazen.library.definitions.User;
import co.uk.immure.brazen.library.entities.Ticket;
import co.uk.immure.brazen.library.exceptions.TicketAdvancementException;
import co.uk.immure.brazen.services.repositories.mem.TransientTicketRepository;
import co.uk.immure.brazen.services.repositories.mem.TransientTicketTypeRepository;
import co.uk.immure.brazen.services.startup.DemoInitialiser;

import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Log
public class MyVaadinApplication extends Application
{
    private Window window;

    @Override
    public void init()
    {
    	
    	new DemoInitialiser().setupBrazen();
    	
        window = new Window("My Vaadin Application");
        setMainWindow(window);

        
        
        Collection<TicketType> ticketTypes = TransientTicketTypeRepository.getInstance().getAll();
        
        window.addComponent(new Label("I know about: "));
        
        for (TicketType tt : ticketTypes) {
        	window.addComponent(new Label(tt.getName()));
        }
        
        window.addComponent(new Label("These are the current tickets: "));
        
        final Panel currentTickets = new Panel();
        
        
        
        for (Ticket t : TransientTicketRepository.getInstance().getAll()) {
        	addTicket(t, currentTickets);
        }
        
        window.addComponent(currentTickets);
        
        Button button = new Button("New Incident");
        button.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                TicketType tt = TransientTicketTypeRepository.getInstance().get("Incident");
                log.info(tt.toString());
                Ticket t = Ticket.start(tt,Long.parseLong(TransientTicketRepository.getInstance().createUniqueId()), new User("test"));
                log.info(t.toString());
                TransientTicketRepository.getInstance().save(t);
                log.info(TransientTicketRepository.getInstance().getAll().toString());
                addTicket(t, currentTickets);
                window.requestRepaintAll();
            }
        });
        
        window.addComponent(button);
        
        
    }
    
    protected void addTicket(final Ticket t, Panel panel) {
    	HorizontalLayout layout = new HorizontalLayout();
    	Link link = new Link(t.getId(), t);
    	final Label l = new Label(" - " + t.getCurrentStage().getStageName());
    	final ComboBox combo = new ComboBox();
    	setStages(combo, t);
    	Button button = new Button("Save");
    	final Label errorLabel = new Label();
    	button.addListener(new Button.ClickListener() {
			
			public void buttonClick(ClickEvent event) {
				String v = (String) combo.getValue();
				for (TicketStage ts : t.getCurrentStage().getNextStages()) {
					if (ts.getStageName().equals(v)) {
						try {
							t.advanceStage(ts);
						} catch (TicketAdvancementException e) {
							log.warning(e.getMessage());
							errorLabel.setValue(e.getMessage());
						}
						TransientTicketRepository.getInstance().save(t);
						l.setValue(t.getId() + " - " + t.getCurrentStage().getStageName());
						setStages(combo, t);
					}
				}
				
			}
		});
    	
    	layout.addComponent(l);
    	layout.addComponent(combo);
    	layout.addComponent(button);
    	layout.addComponent(errorLabel);
    	panel.addComponent(layout);
    	
    }
    
    protected void setStages(ComboBox box, Ticket t) {
    	box.removeAllItems();
    	for (TicketStage ts : t.getCurrentStage().getNextStages()) {
    		box.addItem(ts.getStageName());
    	}
    }
    
    
    
}
