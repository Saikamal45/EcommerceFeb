package com.ecommerce.cartservice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import feign.RequestInterceptor;

@Configuration
public class FeignHeaderConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {

            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();

            if (auth != null) {

                // Forward roles
                String roles = auth.getAuthorities().stream()
                        .map(a -> a.getAuthority().replace("ROLE_", ""))
                        .reduce((a, b) -> a + "," + b)
                        .orElse(null);

                if (roles != null) {
                    requestTemplate.header("X-Roles", roles);
                }

                // Forward userId
                Object details = auth.getDetails();
                if (details != null) {
                    requestTemplate.header("X-User-Id", details.toString());
                }
            }
        };
    }
}

