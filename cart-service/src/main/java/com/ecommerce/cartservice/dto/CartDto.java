package com.ecommerce.cartservice.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {

	private Long id;
    private int userId;
    private BigDecimal totalAmount;
    private List<CartItemDto> items = new ArrayList<>(); 
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
