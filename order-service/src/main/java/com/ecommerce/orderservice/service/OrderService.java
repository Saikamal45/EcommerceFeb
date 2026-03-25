package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.dto.ProductOrderDto;
import com.ecommerce.orderservice.entity.OrderStatus;
import com.ecommerce.orderservice.exception.OrderNotFoundException;

public interface OrderService {

	ProductOrderDto order();
	
	ProductOrderDto getOrders(Long orderId) throws OrderNotFoundException;
	
	OrderStatus getOrderStatus(Long orderId)throws OrderNotFoundException;
	
	ProductOrderDto changeOrderStatus(Long orderId, OrderStatus status)throws OrderNotFoundException;
}
