package com.ecommerce.cartservice.exception;

public class ProductServiceUnavailable extends RuntimeException{

	public ProductServiceUnavailable(String msg) {
		super(msg);
	}
}
