package com.ecommerce.orderservice.feignClient;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="notification-service")
public interface NotificationClient {

	@PostMapping("/sendOrderSuccessMail")
	String orderSuccess(@RequestParam String email,@RequestParam Long orderId,@RequestParam BigDecimal totalAmount);
}
