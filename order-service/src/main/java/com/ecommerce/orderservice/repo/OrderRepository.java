package com.ecommerce.orderservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;
import org.springframework.stereotype.Repository;

import com.ecommerce.orderservice.dto.CartDto;
import com.ecommerce.orderservice.dto.ProductOrderDto;
import com.ecommerce.orderservice.entity.ProductOrder;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, Long>{

	ProductOrderDto save(CartDto cart);

}
