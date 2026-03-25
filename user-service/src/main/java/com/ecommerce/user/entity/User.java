package com.ecommerce.user.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
@Schema(
    name = "User",
    description = "Represents a user in the e-commerce system"
)
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Unique identifier of the user",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private int id;

    @Schema(
        description = "First name of the user",
        example = "Sai"
    )
    private String firstName;

    @Schema(
        description = "Last name of the user",
        example = "Kamal"
    )
    private String lastName;

    @Schema(
        description = "Email address of the user (used as username)",
        example = "saikamal@gmail.com",
        requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Schema(
        description = "User password (never exposed in API responses)",
        example = "Strong@123",
        accessMode = Schema.AccessMode.WRITE_ONLY
    )
    private String password;

    @Schema(
        description = "Registered mobile number",
        example = "9876543210"
    )
    private long phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Schema(
        description = "List of addresses associated with the user",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private List<Address> address;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL, CascadeType.REMOVE })
    @JoinTable(
        name = "user_roles",
        joinColumns = { @JoinColumn(name = "user_id") },
        inverseJoinColumns = { @JoinColumn(name = "role_name") }
    )
    @Schema(
        description = "Roles assigned to the user",
        example = "[\"CUSTOMER\", \"ADMIN\"]"
    )
    private Set<Role> roles;

    // ---------------- SECURITY METHODS ----------------

    @Override
    @JsonIgnore
    @Schema(hidden = true)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> authorities = new HashSet<>();
        this.roles.forEach(userRole -> {
            authorities.add(new Authority("ROLE_" + userRole.getRoleName()));
        });
        return authorities;
    }

    @Override
    @JsonIgnore
    @Schema(hidden = true)
    public String getUsername() {
        return this.email;
    }

    @Override
    @JsonIgnore
    @Schema(hidden = true)
    public String getPassword() {
        return this.password;
    }
}