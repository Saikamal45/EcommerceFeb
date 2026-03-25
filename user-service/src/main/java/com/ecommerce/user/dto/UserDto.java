package com.ecommerce.user.dto;

import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private long phoneNumber;
	private List<AddressDto> address;
	private Set<String> roles;
}
