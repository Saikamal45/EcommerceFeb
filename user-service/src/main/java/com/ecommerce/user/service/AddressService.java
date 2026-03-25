package com.ecommerce.user.service;

import com.ecommerce.user.entity.Address;
import com.ecommerce.user.exception.UserNotFoundException;

public interface AddressService {

	Address saveAddress(Address address,String email) throws UserNotFoundException;
}
