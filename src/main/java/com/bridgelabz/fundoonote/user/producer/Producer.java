package com.bridgelabz.fundoonote.user.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonote.user.model.MailDTO;
@Component
public class Producer implements IProducer{
	@Autowired
	private AmqpTemplate amqpTemplate;
	
	@Value("${jsa.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${jsa.rabbitmq.routingkey}")
	private String routingKey;
	@Override
	public void produceMsg(MailDTO mailDTO){
		amqpTemplate.convertAndSend(exchange,routingKey, mailDTO);
		System.out.println("Send msg = " + mailDTO);
	}
}
