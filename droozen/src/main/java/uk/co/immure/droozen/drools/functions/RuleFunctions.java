package uk.co.immure.droozen.drools.functions;


import org.apache.log4j.Logger;
import org.drools.spi.KnowledgeHelper;

public class RuleFunctions {
	
	
	
	public static void log(final KnowledgeHelper drools, final String message, final Object... parameters) {
		final String category = drools.getRule().getPackageName() + "." + drools.getRule().getName();
		final String formattedMessage = String.format(message, parameters);
		Logger.getLogger(category).info(formattedMessage);
	}

}
