package com.tenant.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	/**
	 * Extracts ownerId from the JWT token. Tries to get "ownerId" claim first, then
	 * falls back to "userId". Returns null if neither is present.
	 */
	public Long extractOwnerId(String token) {
		Claims claims = extractAllClaims(token);
		Object ownerIdObj = claims.get("ownerId");
		if (ownerIdObj == null) {
			ownerIdObj = claims.get("userId");
		}
		if (ownerIdObj == null) {
			return null;
		}
		if (ownerIdObj instanceof Integer) {
			return ((Integer) ownerIdObj).longValue();
		}
		return (Long) ownerIdObj;
	}
}