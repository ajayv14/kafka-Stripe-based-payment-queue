package com.ib.stripe.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.ib.stripe.app.Entity.ChargeRequest;
import com.ib.stripe.app.Entity.ChargeRequest.Currency;
import com.ib.stripe.app.service.MessageQueueService;
import com.ib.stripe.app.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class MainController {
	
	int purchase_hit_count=0;
	
	@Autowired
	private PaymentService paymentService;	
	
	@Autowired
	private MessageQueueService messageQueueService;
	
	@RequestMapping("/products")
	public String productsPage() {
		return "products.html";		
	}
	
	
	@ResponseBody
	@RequestMapping("/hello")
	public Charge helloWorld() throws StripeException {
	
		ChargeRequest chargeRequest = new ChargeRequest();
		chargeRequest.setAmount(100);
		chargeRequest.setCurrency(Currency.USD);
		chargeRequest.setStripeEmail("aj@mail.com");
		chargeRequest.setStripeToken("tok_visa");
		chargeRequest.setDescription("Chocolates");
		return paymentService.chargeTransaction(chargeRequest);			
				
	}	
		
	@RequestMapping(value="/purchase",method=RequestMethod.POST)
	@ResponseBody
	public String buySomething(@RequestParam("productId") String productId) throws StripeException {		
		ChargeRequest chargeRequest = new ChargeRequest();
		chargeRequest.setAmount(100);
		chargeRequest.setCurrency(Currency.USD);
		chargeRequest.setStripeEmail("aj@mail.com");
		chargeRequest.setStripeToken("tok_visa");
		chargeRequest.setDescription(productId+"---"+purchase_hit_count+" cus no");
		
		//log.info("sent to queue--- "+purchase_hit_count);
		
		purchase_hit_count++;
		return messageQueueService.sendOrderToQueue(chargeRequest);
	}
	
	
	
	
}
