package com.ib.stripe.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ib.stripe.app.Entity.ChargeRequest;

import lombok.extern.slf4j.Slf4j;

/*pushes the transaction order details to the kafka message queue */

@Service
@Slf4j
public class MessageQueueService {
	
	@Autowired
	private KafkaTemplate<String,ChargeRequest> kafkaTemplate;
	
	public String sendOrderToQueue(ChargeRequest chargeRequest) {
		kafkaTemplate.send("test",chargeRequest); //topic name set to test
		//kafkaTemplate.send("test","sr001");
			
		return "ack";		
	}
	
	
}
