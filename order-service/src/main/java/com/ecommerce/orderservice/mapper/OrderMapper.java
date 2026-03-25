package com.ecommerce.orderservice.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.ecommerce.orderservice.dto.OrderItemDto;
import com.ecommerce.orderservice.dto.ProductOrderDto;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.entity.ProductOrder;

@Mapper(componentModel = "spring")
public interface OrderMapper {

	ProductOrder toEntity(ProductOrderDto productOrderDto);
	ProductOrderDto toDto(ProductOrder productOrder);

    OrderItemDto toDto(OrderItem item);

    List<OrderItemDto> toItemDtoList(List<OrderItem> items);
}
