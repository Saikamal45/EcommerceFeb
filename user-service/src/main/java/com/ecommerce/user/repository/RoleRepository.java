package com.ecommerce.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.user.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
	Optional<Role> findByRoleName(String roleName);
}
