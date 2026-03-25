package com.ecommerce.notificationservice.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.notificationservice.emailUtil.EmailUtil;
import jakarta.mail.MessagingException;

@RestController
public class EmailController {

	@Autowired
	private EmailUtil emailUtil;
	
	@PostMapping("/sendAccountCreationMail")
	public ResponseEntity<String> accountCreationMail(@RequestParam String email) throws UnsupportedEncodingException, MessagingException{
		String accountCreationMail = emailUtil.accountCreationMail(email);
		return new ResponseEntity<String>(accountCreationMail,HttpStatus.OK);
	}
	
	@PostMapping("/sendProductAddedToCartEmail")
	public ResponseEntity<String> productAddedToCart(@RequestParam String email,@RequestParam String productName,@RequestParam double price) throws UnsupportedEncodingException, MessagingException{
		 String productAddToCart = emailUtil.productAddToCart(email, productName, price);
		return new ResponseEntity<String>(productAddToCart,HttpStatus.OK);
	}
	
	@PostMapping("/sendOrderSuccessMail")
	public ResponseEntity<String> orderSuccess(@RequestParam String email,@RequestParam Long orderId,@RequestParam BigDecimal totalAmount) throws UnsupportedEncodingException, MessagingException{
		 String orderSuccessMail = emailUtil.orderSuccessMail(email, orderId, totalAmount);
		return new ResponseEntity<String>(orderSuccessMail,HttpStatus.OK);
	}
}
