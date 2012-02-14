package uk.co.immure.brazen.camel;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultProducer;

public class GenericComponent extends DefaultComponent {
	
	@Override
	public Endpoint createEndpoint(String uri) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("action", "");
		return createEndpoint(uri, "", params);
		
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining,
			Map<String, Object> parameters) throws Exception {
		
		Endpoint endpoint = new DefaultEndpoint() {
			
			@Override
			protected String createEndpointUri() {
				return "repository";
			}
			
			@Override
			public boolean isSingleton() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public ExchangePattern getExchangePattern() {
				return ExchangePattern.InOut;
			}
			
			@Override
			public Producer createProducer() throws Exception {
				Producer p = new DefaultProducer(this) {
					
					@Override
					public void process(Exchange exchange) throws Exception {
						System.out.println("called Process()");
						System.out.println(exchange.getClass());
						System.out.println(exchange.getIn().getBody());
						System.out.println(exchange.getIn().getHeaders());
						System.out.println(exchange.getIn().getAttachmentNames());
						
						
						
						Integer i = (Integer) exchange.getIn().getBody();
						i = i + 1;
						exchange.getOut().setBody(i);
						
					}
				};
				p.start();
				return p;
			}
			
			@Override
			public Consumer createConsumer(Processor processor) throws Exception {
//				Consumer c = new DefaultConsumer(this, processor) {
//					
//					
//					
//					
//					
//				};
//				c.start();
//				return c;
				return null;
			}
		};
		
		return endpoint;
		
	}

}
