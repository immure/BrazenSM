package uk.co.immure.brazen.camel;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Component(value="myProducer")
public class MyProducer {
	
	@Produce(uri="vm:repository:ticket:save")
	protected ProducerTemplate template;
	
	public void sendTestTicket() {
		
		template.sendBody("test");
		
	}

}
