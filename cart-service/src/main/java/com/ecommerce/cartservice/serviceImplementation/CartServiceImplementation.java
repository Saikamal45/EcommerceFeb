package com.ecommerce.cartservice.serviceImplementation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.cartservice.dto.ProductDto;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;
import com.ecommerce.cartservice.exception.CartException;
import com.ecommerce.cartservice.exception.ProductServiceUnavailable;
import com.ecommerce.cartservice.feignClient.NotificationClient;
import com.ecommerce.cartservice.feignClient.ProductClient;
import com.ecommerce.cartservice.repository.CartRepository;
import com.ecommerce.cartservice.service.CartService;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import static com.ecommerce.cartservice.security.SecurityUtil.getCurrentUserId;
import static com.ecommerce.cartservice.security.SecurityUtil.getCurrentUserEmail;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class CartServiceImplementation implements CartService{
	
	private static final Logger log =
	        LoggerFactory.getLogger(CartServiceImplementation.class);
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private NotificationClient notificationClient;

	@Override
	//@CircuitBreaker(name = "productServiceCB",fallbackMethod = "addToCartFallBack")
	@Retry(name = "productServiceRetry",fallbackMethod = "addToCartFallBack")
	//@RateLimiter(name = "productServiceLimiter",fallbackMethod = "addToCartFallBack")
	public Cart addToCart(Long ProductId) throws CartException {
		  Long userId = getCurrentUserId();
		  String currentUserEmail = getCurrentUserEmail();
		  log.info("Calling addToCart() method");
		 ProductDto product;
		    try {
		        product = productClient.getProductById(ProductId);
		        
		    } catch (FeignException.NotFound ex) {
		        throw new CartException("Product not found with id: " + ProductId);
		    }

		    Cart cart = cartRepository.findByUserId(userId)
		    	    .orElseGet(() -> {
		    	        Cart c = new Cart();
		    	        c.setUserId(userId.intValue()); // because userId is int in entity
		    	        c.setTotalAmount(BigDecimal.ZERO);
		    	        c.setCreatedAt(LocalDateTime.now());
		    	        return c;
		    	    });


		 //STEP 1: check if product already exists in cart
		    CartItem existingItem = cart.getItems()
		            .stream()
		            .filter(i -> i.getProductId().equals(product.getProductId()))
		            .findFirst()
		            .orElse(null);

		    if (existingItem != null) {
		        // ✅ Product already in cart → increase quantity
		        existingItem.setQuantity(existingItem.getQuantity() + 1);
		    } else {
		        // ✅ New product → add new CartItem
		        CartItem item = new CartItem();
		        item.setProductId(product.getProductId());
		        item.setProductCode(product.getProductCode());
		        item.setPrice(BigDecimal.valueOf(product.getPrice()));
		        item.setQuantity(1);
		        item.setCart(cart);

		        cart.getItems().add(item);
		    }
           
		    cart.setTotalAmount(
		        cart.getItems().stream()
		            .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
		            .reduce(BigDecimal.ZERO, BigDecimal::add)
		    );
		    cart.setUpdatedAt(LocalDateTime.now());
		    notificationClient.productAddedToCart(currentUserEmail, product.getProductName(), product.getPrice());
		    return cartRepository.save(cart);

}

	public Cart addToCartFallBack(Long productId, Throwable ex) {

        throw new ProductServiceUnavailable(
	        "Can't add this product now. Product service is temporarily unavailable. Please try later."
	    );
	}

	@Override
	public String deleteCart() throws CartException {
		Long userId = getCurrentUserId();
		
		 Cart cart = cartRepository.findByUserId(userId)
		            .orElseThrow(() ->
		                new CartException("Cart not found for user id: " + userId)
		            );
		

		    cartRepository.delete(cart);
		return "Deleted SucessFully";
	}

	@Override
	public Cart deleteCartItem(Long productId) throws CartException {
		Long userId = getCurrentUserId();
		
		ProductDto product;
	    try {
	        product = productClient.getProductById(productId);
	    } catch (FeignException.NotFound ex) {
	        throw new CartException("Product not found with id: " + productId);
	    }
	    
	    // Finding the cart for the user
		 Cart cart = cartRepository.findByUserId(userId)
		            .orElseThrow(() ->
		                new CartException("Cart not found for user id: " + userId)
		            );
		 //Find the specific product inside the cart
		 CartItem existingItem = cart.getItems()
		            .stream()
		            .filter(i -> i.getProductId().equals(product.getProductId()))
		            .findFirst()
		            .orElse(null);

		 if (existingItem == null) {
			    throw new CartException("Product not found in cart");
			}

			if (existingItem.getQuantity() > 1) {
			    // Case 2 → reduce quantity
			    existingItem.setQuantity(existingItem.getQuantity() - 1);
			} else {
			    // Case 3 → quantity == 1 → remove item
			    cart.getItems().remove(existingItem);
			}

		    
		    //Recalculate cart total
		    cart.setTotalAmount(
			        cart.getItems().stream()
			            .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
			            .reduce(BigDecimal.ZERO, BigDecimal::add)
			    );
			    cart.setUpdatedAt(LocalDateTime.now());
			    
		return cartRepository.save(cart);
	}

	@Override
	public Cart getCart() throws CartException {
		Long userId = getCurrentUserId();
		Cart cart = cartRepository.findByUserId(userId)
	            .orElseThrow(() ->
	                new CartException("Cart not found for user id: " + userId)
	            );
		return cart;
	}

	@Override
	public Cart updateItemQuantity(Long productId, Integer quantity) throws CartException {
		Long userId = getCurrentUserId();
		
		
		ProductDto product;
	    try {
	        product = productClient.getProductById(productId);
	    } catch (FeignException.NotFound ex) {
	        throw new CartException("Product not found with id: " + productId);
	    }
	    
	    
		Cart cart = cartRepository.findByUserId(userId)
	            .orElseThrow(() ->
	                new CartException("Cart not found for user id: " + userId)
	            );
		
		//Find the specific product inside the cart
		 CartItem existingItem = cart.getItems()
		            .stream()
		            .filter(i -> i.getProductId().equals(product.getProductId()))
		            .findFirst()
		            .orElse(null);
		 
		 if(existingItem.getProductId()!=null) {
			 existingItem.setQuantity(quantity);
		 }
		return cartRepository.save(cart);
	}
}
