package com.ecommerce.cartservice.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDto {

	private Long id;

    private String productId;

    private String productCode;

    private Integer quantity;

    private BigDecimal price;
    
}
