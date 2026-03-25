package com.ecommerce.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.orderservice.dto.ProductOrderDto;
import com.ecommerce.orderservice.entity.OrderStatus;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.service.OrderService;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PreAuthorize("hasRole('Customer')")
	@PostMapping("/createOrder")
	public ResponseEntity<ProductOrderDto> createOrder(){
		ProductOrderDto order = orderService.order();
		return new ResponseEntity<ProductOrderDto>(order,HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('Customer')")
	@GetMapping("/getOrderById")
	public ResponseEntity<ProductOrderDto> getOrderById(@RequestParam Long orderId) throws OrderNotFoundException{
		ProductOrderDto orders = orderService.getOrders(orderId);
		return new ResponseEntity<ProductOrderDto>(orders,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Customer')")
	@GetMapping("/getOrderStatus")
	public ResponseEntity<OrderStatus> getOrderStatus(@RequestParam Long orderId) throws OrderNotFoundException{
		OrderStatus orderStatus = orderService.getOrderStatus(orderId);
		return new ResponseEntity<OrderStatus>(orderStatus,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@PatchMapping("/changeOrderStatus")
	public ResponseEntity<ProductOrderDto> changeOrderStatus(@RequestParam Long orderId,@RequestParam OrderStatus status) throws OrderNotFoundException{
		ProductOrderDto changeOrderStatus = orderService.changeOrderStatus(orderId, status);
		return new ResponseEntity<ProductOrderDto>(changeOrderStatus,HttpStatus.OK);
	}
}
