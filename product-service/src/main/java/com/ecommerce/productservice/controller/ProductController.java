package com.ecommerce.productservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.exceptions.ProductNotException;
import com.ecommerce.productservice.service.ProductService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	
	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/add")
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
		ProductDto product = productService.addProduct(productDto);
		return new ResponseEntity<ProductDto>(product,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getProductByName")
	public ResponseEntity<List<ProductDto>> getProductByName(@RequestParam String productName) throws ProductNotException{
		List<ProductDto> productByName = productService.getProductByName(productName);
		return new ResponseEntity<List<ProductDto>>(productByName,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','Customer')")
	@GetMapping("/getProductById/{productId}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long productId) throws ProductNotException{
		ProductDto productById = productService.getProductById(productId);
		return new ResponseEntity<ProductDto>(productById,HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('Admin')")
	@PatchMapping("/updateProduct")
	public ResponseEntity<ProductDto> updateProduct(@RequestParam Long productId,@RequestBody ProductDto productDto) throws ProductNotException{
		ProductDto updateProduct = productService.updateProduct(productId, productDto);
		return new ResponseEntity<ProductDto>(updateProduct,HttpStatus.OK);	
	}
	
	@PreAuthorize("hasAnyRole('Admin','Customer')")
	@PutMapping("/reduceQuantity")
	public ResponseEntity<ProductDto> reduceProductQuantity(@RequestParam Long productId,@RequestParam int quantity) throws ProductNotException{
		ProductDto reduceQuantity = productService.reduceQuantity(productId, quantity);
		return new ResponseEntity<ProductDto>(reduceQuantity,HttpStatus.OK);
	}
}
