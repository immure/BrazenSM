package uk.co.immure.brazen.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;

public enum BrazenCamelContext {
	
	INSTANCE;
	
	private final CamelContext context;
	
	private BrazenCamelContext() {
		context = new DefaultCamelContext();
				
	}

	public CamelContext getContext() {
		return context;
	}
	
	
	
	

}
