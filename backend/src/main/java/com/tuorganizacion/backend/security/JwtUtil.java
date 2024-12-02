package com.tuorganizacion.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtil {

  private final SecretKey secretKey;
  private final long expiration;

  public JwtUtil(
    @Value("${jwt.secret}") String secret,
    @Value("${jwt.expiration}") long expiration
  ) {
    this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    this.expiration = expiration;
  }

  public String generateToken(String username) {
    Instant now = Instant.now();
    Instant expiry = now.plusMillis(expiration);

    return Jwts.builder()
      .claim("username", username)
      .issuedAt(Date.from(now))
      .expiration(Date.from(expiry))
      .signWith(secretKey)
      .compact();
  }

  public Claims extractAllClaims(String token) {
    return Jwts.parser()
      .verifyWith(secretKey)
      .build()
      .parseSignedClaims(token)
      .getPayload();
  }

  public String extractUsername(String token) {
    return extractAllClaims(token).get("username", String.class);
  }

  public boolean validateToken(String token, String username) {
    try {
      String extractedUsername = extractUsername(token);
      return extractedUsername.equals(username) && !isTokenExpired(token);
    } catch (Exception ex) {
      return false;
    }
  }

  public boolean isTokenExpired(String token) {
    return extractAllClaims(token).getExpiration().before(new Date());
  }
}