package com.ecommerce.orderservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.orderservice.dto.ProductDto;
import com.ecommerce.orderservice.security.FeignHeaderConfig;

@FeignClient(name = "product-service",configuration = FeignHeaderConfig.class)
public interface ProductClient {

	@PutMapping("/products/reduceQuantity")
	ProductDto reduceProductQuantity(@RequestParam Long productId, @RequestParam int quantity);
}
