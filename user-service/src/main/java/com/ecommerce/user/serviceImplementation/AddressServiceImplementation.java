 package com.ecommerce.user.serviceImplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecommerce.user.entity.Address;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.UserNotFoundException;
import com.ecommerce.user.repository.AddressRepository;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.service.AddressService;

@Service
public class AddressServiceImplementation implements AddressService{
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Address saveAddress(Address address,String email) throws UserNotFoundException {
		User user = userRepository.findByEmail(email);
		if(user==null) {
			throw new UserNotFoundException("User Not Found with email :"+email);		
		}
		address.setUser(user);
		return addressRepository.save(address);
	}

}
