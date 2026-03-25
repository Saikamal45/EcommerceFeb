package com.ecommerce.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.user.exception.UserServiceDown;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class JwtController {

    @Autowired
    private JwtImplementation jwtServiceImplementation;

    @PostMapping("/login")
    @RateLimiter(name = "userServiceLimiter",fallbackMethod = "loginFallBack")
    public JwtResponse generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
    	 System.out.println("LOGIN API HIT at " + java.time.LocalTime.now());
        return jwtServiceImplementation.createJwtToken(jwtRequest);
    }
    
    
    public JwtResponse loginFallBack(@RequestBody JwtRequest jwtRequest,Throwable ex) throws UserServiceDown {
    	throw new UserServiceDown(
    	        "Can't Login now. User service is down. Please try later."
    	    );
    }
}