package com.ecommerce.cartservice.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.cartservice.dto.CartDto;
import com.ecommerce.cartservice.dto.CartItemDto;
import com.ecommerce.cartservice.entity.Cart;
import com.ecommerce.cartservice.entity.CartItem;

@Mapper(componentModel = "spring")
public interface CartMapper {

	Cart toEntity(CartDto cartDto);
	CartDto toDto(Cart cart);
	CartItem toEntity(CartItemDto cartItemDto);
	CartItemDto toDto(CartItem cartItem);
}
