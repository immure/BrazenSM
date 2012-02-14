package uk.co.immure.brazen.camel;

import java.util.Map;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultComponent;
import org.apache.camel.impl.DefaultConsumer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducer;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.spring.Main;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PlayArea {

	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("brazen.xml");
		ctx.start();
		CamelContext c = ctx.getBean(CamelContext.class);
		
		MyProducer mp = ctx.getBean(MyProducer.class);
		mp.sendTestTicket();
		
//		ProducerTemplate p = c.createProducerTemplate();
//		
//		Object o = p.requestBody("vm:repository:ticket:save","Test");
//		System.out.println(o);
		
		
		

//		DefaultCamelContext ctx = new DefaultCamelContext();
//
//		// JndiRegistry jndi = new JndiRegistry();
//		// jndi.bind("repository", new MyProcessor());
//		// ctx.setRegistry(jndi);
//
//		ctx.addComponent("repository", new GenericComponent());
//
//		ctx.addRoutes(new RouteBuilder() {
//			@Override
//			public void configure() {
//				from("repository:ticket").process(new MyProcessor());
//
//			}
//		});
//
//		ProducerTemplate template = ctx.createProducerTemplate();
//		ctx.start();
//
//		Object o = template.requestBody("repository:ticket?action=save", new Integer(
//				41));
//		System.out.println("\t" + o);

	}

	static class MyProcessor implements Processor {
		@Override
		public void process(Exchange arg0) throws Exception {
			System.out.println(arg0.getClass());
			System.out.println(arg0.getIn().getBody());
			System.out.println(arg0.getIn().getHeaders());
			System.out.println(arg0.getIn().getAttachmentNames());

		}
	}

}
