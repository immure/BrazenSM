package uk.co.immure.brazen.camel;

import org.apache.camel.Consume;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

@Service
public class TicketRouter {
	
	
	
	@Consume(uri="vm:repository:ticket:save")
	public void save(String id) {
		System.out.println("Received: " + id);
	}
	
	@Consume(uri="vm:repository:ticket:save")
	public void save2(String id) {
		System.out.println("Also Received: " + id);
	}
	
	
	

}
