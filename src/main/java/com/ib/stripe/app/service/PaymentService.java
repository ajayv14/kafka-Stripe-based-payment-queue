package com.ib.stripe.app.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ib.stripe.app.Entity.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
/* The service reads secret key from .properties file and performs a simple transaction using the stripe payment api*/
@Service
public class PaymentService {
	
	@Value("${STRIPE_SECRET_KEY}")
	private String secretKey;
	
	@PostConstruct
	public void init() {
		Stripe.apiKey=secretKey;
	}
	
	public Charge chargeTransaction(ChargeRequest chargeRequest) throws StripeException {
		
		 Map<String, Object> pchargePaymentParams = new HashMap<String, Object>();
		pchargePaymentParams.put("amount", chargeRequest.getAmount());
		pchargePaymentParams.put("currency",chargeRequest.getCurrency());
		pchargePaymentParams.put("source", chargeRequest.getStripeToken());
		pchargePaymentParams.put("receipt_email", chargeRequest.getStripeEmail());
		pchargePaymentParams.put("description",chargeRequest.getDescription());
		
		return Charge.create(pchargePaymentParams);		
	}
	
	
}
