package com.ecommerce.orderservice.serviceImplementation;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import com.ecommerce.orderservice.dto.CartDto;
import com.ecommerce.orderservice.dto.ProductOrderDto;
import com.ecommerce.orderservice.entity.OrderItem;
import com.ecommerce.orderservice.entity.OrderStatus;
import com.ecommerce.orderservice.entity.ProductOrder;
import com.ecommerce.orderservice.exception.OrderNotFoundException;
import com.ecommerce.orderservice.feignClient.CartClient;
import com.ecommerce.orderservice.feignClient.NotificationClient;
import com.ecommerce.orderservice.feignClient.ProductClient;
import com.ecommerce.orderservice.mapper.OrderMapper;
import com.ecommerce.orderservice.repo.OrderRepository;
import com.ecommerce.orderservice.security.SecurityUtil;
import com.ecommerce.orderservice.service.OrderService;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CartClient  cartClient;
	
	@Autowired
	private ProductClient productClient;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private NotificationClient notificationClient;
	
	@Autowired
	private SecurityUtil securityUtil;

	@Override
	@Transactional
	public ProductOrderDto order() {
		//String currentUserEmail = getCurrentUserEmail();
		String currentUserEmail =securityUtil.getCurrentUserEmail();
	    CartDto cart = cartClient.getCart();

	    ProductOrder order = new ProductOrder();
	    order.setTotal(cart.getTotalAmount());
	    order.setOrderDate(LocalDateTime.now());
	    order.setOrderStatus(OrderStatus.CREATED);
	    
	 // ✅ Convert cart items to order items
	    cart.getItems().forEach(cartItem -> {

	        OrderItem orderItem = new OrderItem();
	        orderItem.setProductId(cartItem.getProductId());
	        orderItem.setPrice(cartItem.getPrice());
	        orderItem.setQuantity(cartItem.getQuantity());

	        // 🔥 VERY IMPORTANT (owning side)
	        orderItem.setProductOrder(order);

	        order.getOrderItems().add(orderItem);
	    });

	    
	    ProductOrder savedOrder = orderRepository.save(order);
	    
	    notificationClient.orderSuccess(currentUserEmail, savedOrder.getOrderId(), savedOrder.getTotal());
	  
	 
	 // ✅ Execute AFTER successful DB commit
	    TransactionSynchronizationManager.registerSynchronization(
	        new TransactionSynchronization() {

	            @Override
	            public void afterCommit() {
	                
	                savedOrder.getOrderItems().forEach(item->{
	                	productClient.reduceProductQuantity(item.getProductId(), 
	                			item.getQuantity());
	                });
	                
	                cartClient.deleteCart();
	            }

	            // other methods optional
	            @Override public void beforeCommit(boolean readOnly) {}
	            @Override public void beforeCompletion() {}
	            @Override public void afterCompletion(int status) {}
	            @Override public void suspend() {}
	            @Override public void resume() {}
	            @Override public void flush() {}
	        }
	    );
	    
	   

	    return orderMapper.toDto(savedOrder);
	}

	@Override
	public ProductOrderDto getOrders(Long orderId) throws OrderNotFoundException{
		ProductOrder orderById = orderRepository.findById(orderId).
		orElseThrow(()->new OrderNotFoundException("No Order Found with id : "+orderId));
		
		return orderMapper.toDto(orderById);
	}

	@Override
	public OrderStatus getOrderStatus(Long orderId) throws OrderNotFoundException {
		ProductOrder orderById = orderRepository.findById(orderId).
				orElseThrow(()->new OrderNotFoundException("No Order Found with id : "+orderId));
		
		OrderStatus orderStatus = orderById.getOrderStatus();
		return orderStatus;
	}

	@Override
	public ProductOrderDto changeOrderStatus(Long orderId,OrderStatus status) throws OrderNotFoundException {
		ProductOrder orderById = orderRepository.findById(orderId).
				orElseThrow(()->new OrderNotFoundException("No Order Found with id : "+orderId));
		
		orderById.setOrderStatus(status);
		
		ProductOrder save = orderRepository.save(orderById);
		return orderMapper.toDto(save);
	}


}
