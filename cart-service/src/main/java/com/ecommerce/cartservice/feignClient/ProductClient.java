package com.ecommerce.cartservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.ecommerce.cartservice.dto.ProductDto;
import com.ecommerce.cartservice.security.FeignHeaderConfig;

@FeignClient(name="product-service",configuration = FeignHeaderConfig.class)
public interface ProductClient {

	@GetMapping("/products/getProductById/{productId}")
	ProductDto getProductById(@PathVariable("productId") Long ProductId);
}
