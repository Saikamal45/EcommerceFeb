package com.ecommerce.orderservice.dto;


import java.math.BigDecimal;

import jakarta.persistence.criteria.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {
	private Long orderItemId;

    private Long productId;
    private String productName;

    private BigDecimal price;
    private Integer quantity;

    private Order order;
}
