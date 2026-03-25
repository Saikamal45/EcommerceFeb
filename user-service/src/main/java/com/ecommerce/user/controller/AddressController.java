package com.ecommerce.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.user.entity.Address;
import com.ecommerce.user.exception.UserNotFoundException;
import com.ecommerce.user.service.AddressService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RequestMapping("/address")
@RestController
public class AddressController {

	@Autowired
	private AddressService  addressService;
	
	@SecurityRequirement(name="bearerAuth")
	@PreAuthorize("hasRole('Customer')")
	@PostMapping("/add")
	public ResponseEntity<Address> addAddress(@RequestBody Address address,@RequestParam String email) throws UserNotFoundException{
		Address saveAddress = addressService.saveAddress(address,email);
		return new ResponseEntity<Address>(saveAddress,HttpStatus.OK);
	}
}
