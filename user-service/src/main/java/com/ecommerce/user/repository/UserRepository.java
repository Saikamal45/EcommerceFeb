package com.ecommerce.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.user.dto.UserCreateDto;
import com.ecommerce.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository< User,Integer> {
	User findByEmail(String email);

	User save(UserCreateDto createDto);
}
