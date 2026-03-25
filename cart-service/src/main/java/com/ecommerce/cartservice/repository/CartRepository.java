package com.ecommerce.cartservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{
	 Optional<Cart> findByUserId(Long userId);

	 void deleteById(Optional<Cart> byUserId);

	 
}
