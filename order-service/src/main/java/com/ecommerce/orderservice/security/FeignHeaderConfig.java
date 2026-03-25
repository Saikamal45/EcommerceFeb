package com.ecommerce.orderservice.security;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignHeaderConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {

        return template -> {

            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated()) {
                return;
            }

            Object principal = authentication.getPrincipal();

            if (principal instanceof CustomUserDetails user) {

                // ✅ User identity
                template.header("X-User-Id", String.valueOf(user.getUserId()));
                template.header("X-User-Email", user.getEmail());

                // ✅ Roles
                String roles = authentication.getAuthorities().stream()
                        .map(a -> a.getAuthority().replace("ROLE_", ""))
                        .reduce((a, b) -> a + "," + b)
                        .orElse("");

                template.header("X-Roles", roles);
            }
        };
    }
}
