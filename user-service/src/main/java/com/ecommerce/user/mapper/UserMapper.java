package com.ecommerce.user.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.user.dto.AddressDto;
import com.ecommerce.user.dto.UserCreateDto;
import com.ecommerce.user.dto.UserDto;
import com.ecommerce.user.entity.Address;
import com.ecommerce.user.entity.Role;
import com.ecommerce.user.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {


    // entity -> DTO
    UserDto toUserDto(User user);

    // DTO -> entity
    @Mapping(target = "roles", ignore = true) // you set roles manually in service
    @Mapping(target = "address", ignore = true)
    User toUser(UserCreateDto dto);

    // address mapping
    AddressDto toAddressDto(Address address);

    // custom mapping: Role -> String (roleName)
    default String mapRoleToString(Role role) {
        return role.getRoleName();
    }

    // custom mapping: Set<Role> -> Set<String>
    default Set<String> mapRoles(Set<Role> roles) {
        return roles.stream()
                .map(Role::getRoleName)
                .collect(Collectors.toSet());
    }
}
