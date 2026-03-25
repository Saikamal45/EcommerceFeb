package com.ecommerce.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	private CartService cartService;

	@PreAuthorize("hasRole('Customer')")
	@PostMapping("/add")
	public ResponseEntity<Cart> addToCart(@RequestParam Long productId){
		Cart toCart = cartService.addToCart(productId);
		return new ResponseEntity<Cart>(toCart,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('Customer')")
	@DeleteMapping("/deleteCart")
	public ResponseEntity<String> deleteCart(){
		String deleteCartItem = cartService.deleteCart();
		return new ResponseEntity<String>(deleteCartItem,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Customer')")
	@DeleteMapping("/deleteCartItem/{productId}")
	public ResponseEntity<Cart> deleteCartItem(@PathVariable Long productId){
		Cart deleteCartItem = cartService.deleteCartItem(productId);
		return new ResponseEntity<Cart>(deleteCartItem,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Customer')")
	@GetMapping("/getCart")
	public ResponseEntity<Cart> getCart(HttpServletRequest request){
		Cart cart = cartService.getCart();
		System.out.println("AUTH HEADER = " + request.getHeader("Authorization"));
		 Authentication authentication =
		            SecurityContextHolder.getContext().getAuthentication();

		    System.out.println("Username: " + authentication.getName());
		authentication.getAuthorities().forEach(authority ->
        System.out.println("ROLE = " + authority.getAuthority())
);
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Customer')")
	@PatchMapping("/updateQuantity")
	public ResponseEntity<Cart> updateQuantity(@RequestParam Long productId,@RequestParam Integer quantity){
		Cart updateItemQuantity = cartService.updateItemQuantity(productId, quantity);
		return new ResponseEntity<Cart>(updateItemQuantity,HttpStatus.OK);
	}
	
}
