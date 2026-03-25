package com.ecommerce.cartservice.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain)
            throws ServletException, IOException {
    	
    	String path = request.getRequestURI();

        // ✅ Bypass JWT for actuator health
        if (path.startsWith("/actuator")) {
            chain.doFilter(request, response);
            return;
        }

        String rolesHeader = request.getHeader("X-Roles");
        String userIdHeader = request.getHeader("X-User-Id");
        String emailHeader  = request.getHeader("X-User-Email"); // 👈 ADD THIS

        if (rolesHeader != null && userIdHeader != null && emailHeader != null) {

            List<SimpleGrantedAuthority> authorities =
                    Arrays.stream(rolesHeader.split(","))
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .toList();

            // ✅ Create CustomUserDetails
            CustomUserDetails userDetails = new CustomUserDetails();
            userDetails.setUserId(Long.valueOf(userIdHeader));
            userDetails.setEmail(emailHeader);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            authorities
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
