package uk.co.immure.brazen.cli;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("brazen-cli.xml");
		ctx.start();
		CommandLine c = ctx.getBean(CommandLine.class);
		
	}

}
