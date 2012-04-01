/**
 * 
 */
package uk.co.immure.brazen.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

class MyProcessor implements Processor {
	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("Received: " + exchange.getIn().getBody());
		
	}
}