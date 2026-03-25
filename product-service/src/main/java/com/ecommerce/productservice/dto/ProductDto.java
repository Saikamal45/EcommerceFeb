package com.ecommerce.productservice.dto;

import java.time.LocalDate;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

	private Long productId;
	private String productName;
	private String productCode;
	private int quantity;
	private double price;
	private String brand;
	private String status;
	private String createdBy;
	private LocalDate createdAt; 
}
