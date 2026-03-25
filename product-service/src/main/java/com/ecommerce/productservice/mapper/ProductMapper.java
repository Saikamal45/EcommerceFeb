package com.ecommerce.productservice.mapper;

import org.mapstruct.Mapper;

import com.ecommerce.productservice.dto.ProductDto;
import com.ecommerce.productservice.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	Product toEntity(ProductDto dto);
	ProductDto toDto(Product product);
}
