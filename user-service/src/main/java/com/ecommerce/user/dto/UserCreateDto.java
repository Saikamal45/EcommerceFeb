package com.ecommerce.user.dto;

import java.util.Set;

import com.ecommerce.user.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
	private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;     
    private long phoneNumber;
    private Set<Role> roles;
}

