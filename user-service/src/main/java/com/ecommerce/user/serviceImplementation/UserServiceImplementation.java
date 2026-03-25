package com.ecommerce.user.serviceImplementation;

import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.ecommerce.user.dto.UserCreateDto;
import com.ecommerce.user.dto.UserDto;
import com.ecommerce.user.entity.Role;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.exception.RoleNotFoundException;
import com.ecommerce.user.feignClient.NotificationClient;
import com.ecommerce.user.mapper.UserMapper;
import com.ecommerce.user.repository.RoleRepository;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.service.UserService;

@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private NotificationClient notificationClient;
 
	private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(12);
	@Override
	public void addRoles() {
		Role admin=new Role();
		admin.setRoleName("Admin");
		roleRepository.save(admin);
		
		Role customer=new Role();
		customer.setRoleName("Customer");
		roleRepository.save(customer);
	}


	@Override
	public User addUser(String roleName, UserCreateDto dto) throws RoleNotFoundException {

	    // 1. Fetch role entity from DB
	    Role role = roleRepository.findByRoleName(roleName)
	            .orElseThrow(() -> new RoleNotFoundException("Role Not Found: " + roleName));

	    // 2. Convert DTO → Entity
	    User user = userMapper.toUser(dto);

	    // 3. Encode password
	    user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

	    // 4. Add role to user
	    Set<Role> roles = new HashSet<>();
	    roles.add(role);       

	    user.setRoles(roles);

	    // 5. Save user
	    return userRepository.save(user);
	}


	@Override
	public UserDto registerCustomer(UserCreateDto dto) {
		Role role = roleRepository.findByRoleName("Customer").
		orElseThrow(()-> new RuntimeException("Customer role not configured"));
		
		User user = userMapper.toUser(dto);
		user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
		
		Set<Role> roles = new HashSet<>();
	    roles.add(role);       

	    user.setRoles(roles);
	    
	    User save = userRepository.save(user);
	    
	    notificationClient.accountCreationMail(user.getEmail());
		
		return userMapper.toUserDto(save);
	}
}
