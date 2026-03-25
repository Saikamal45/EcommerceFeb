package com.ecommerce.user.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ecommerce.user.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {

	@Value("${jwt.secret}")
	private String secretkey;

	private SecretKey getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretkey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

//	public String generateToken(String username) {
//		Map<String, Object> claims = new HashMap<>();
// 
//		return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
//				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4)) // Token Expiration Up to 4 hours
//				.and().signWith(getSecretKey()).compact();
//
//	}
	
	public String generateToken(User user) {

	    Map<String, Object> claims = new HashMap<>();

	    // 🔥 ADD ROLES HERE
	    claims.put(
	        "roles",
	        user.getRoles()
	            .stream()
	            .map(role -> role.getRoleName()) // ADMIN / CUSTOMER
	            .toList()
	    );

	    return Jwts.builder()
	            .claims(claims)
	            .subject(user.getEmail())
	            .claim("userId", user.getId())  
	            .issuedAt(new Date(System.currentTimeMillis()))
	            .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4))
	            .signWith(getSecretKey())
	            .compact();
	}



	public String extractUserName(String token) {
		// extract the username from jwt token
		return extractClaim(token, Claims::getSubject);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}
