package com.ib.stripe.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ib.stripe.app.Entity.ChargeRequest;
import com.stripe.exception.StripeException;

import lombok.extern.slf4j.Slf4j;
/*The consumer would automatically pick the new message in the queue and send it to stripe payment processor service*/
@Slf4j
@Service
public class MessageQueueConsumer {
	
	@Autowired
	private PaymentService paymentService;
	
	private int count=0;
	
	@KafkaListener(topics = "test", containerFactory = "kafkaListenerContainerFactory")  //topic name set to test
	public void greetingListener(ChargeRequest chargeRequest) throws StripeException {
		log.info("message count : "+count);
        log.info("messages"+ chargeRequest.toString());
        
        if(count<5) {
        	paymentService.chargeTransaction(chargeRequest);                   	
        }        
        count++; 
       
        
	}
}
