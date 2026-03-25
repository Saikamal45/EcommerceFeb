package com.ecommerce.orderservice.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {

	private Long productId;

   private String productName;

    private Integer quantity;

    private BigDecimal price;
    
}
