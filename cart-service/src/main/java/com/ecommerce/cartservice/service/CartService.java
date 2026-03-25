package com.ecommerce.cartservice.service;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.exception.CartException;

public interface CartService {

	Cart addToCart( Long ProductId) throws CartException;
	
	String deleteCart()throws CartException;
	
	Cart deleteCartItem(Long productId)throws CartException;
	
	Cart getCart()throws CartException;
	
	Cart updateItemQuantity(Long productId, Integer quantity)throws CartException;

}
