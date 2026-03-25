package com.ecommerce.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.ecommerce.orderservice.dto.CartDto;
import com.ecommerce.orderservice.security.FeignHeaderConfig;


@FeignClient(name = "cart-service",configuration = FeignHeaderConfig.class)
public interface CartClient {

	@GetMapping("/cart/getCart")
	 CartDto getCart();
	
	@DeleteMapping("/cart/deleteCart")
	void deleteCart();
}
