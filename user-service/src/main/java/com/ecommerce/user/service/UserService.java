package com.ecommerce.user.service;

import com.ecommerce.user.dto.UserCreateDto;
import com.ecommerce.user.dto.UserDto;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.RoleNotFoundException;


public interface UserService {

	void addRoles();
	
	User addUser(String role,UserCreateDto createDto) throws RoleNotFoundException;
	
	UserDto registerCustomer(UserCreateDto dto);
}
