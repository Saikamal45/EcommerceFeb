package com.ecommerce.cartservice.feignClient;

import java.math.BigDecimal;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="notification-service")
public interface NotificationClient {

	@PostMapping("/sendProductAddedToCartEmail")
	String productAddedToCart(@RequestParam String email,@RequestParam String productName,@RequestParam double price);
}
