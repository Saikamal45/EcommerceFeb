package com.ecommerce.productservice.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.exceptions.ProductNotException;


public interface ProductService {

	ProductDto addProduct(ProductDto productDto);
	
	List<ProductDto> getProductByName(String productName) throws ProductNotException;
	
	ProductDto getProductById(Long productId)throws ProductNotException;
	
	ProductDto updateProduct(Long productId,ProductDto productDto) throws ProductNotException;
	
	ProductDto reduceQuantity(Long productId,int quantity)throws ProductNotException;
	
}
