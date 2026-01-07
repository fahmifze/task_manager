package com.taskmanager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service // Marks this as a Spring service component
public class JwtService {

    // Secret key for signing JWT tokens (256-bit key encoded in base64)
    // In production, this should be in application.yml or environment variable!
    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    // Token expires after 24 hours (in milliseconds)
    // 24 hours = 24 * 60 * 60 * 1000 = 86400000 ms
    private static final long EXPIRATION_TIME = 86400000;

    // Generate JWT token for a user (using email as username)
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); // Extra data to store in token (empty for now)
        return createToken(claims, username);
    }

    // Build the actual JWT token
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) // Add extra claims (empty map)
                .setSubject(username) // Set the username/email as subject
                .setIssuedAt(new Date(System.currentTimeMillis())) // Token created now
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Token expires in 24h
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Sign with secret key using HS256
                .compact(); // Build the token string
    }

    // Extract username/email from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic method to extract any claim from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse and extract all claims from token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use same key to verify signature
                .build()
                .parseClaimsJws(token) // Parse the token
                .getBody(); // Get the claims (payload)
    }

    // Check if token is valid (matches username and not expired)
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check if token has expired
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Convert secret key string to Key object for signing
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

// ============================================
// JWT TOKEN STRUCTURE
// ============================================
//
// A JWT token has 3 parts separated by dots:
// xxxxx.yyyyy.zzzzz
//
// 1. Header (xxxxx) - Algorithm and token type
//    { "alg": "HS256", "typ": "JWT" }
//
// 2. Payload (yyyyy) - Claims/data
//    { "sub": "john@example.com", "iat": 1234567890, "exp": 1234654290 }
//
// 3. Signature (zzzzz) - Verification
//    HMACSHA256(base64(header) + "." + base64(payload), SECRET_KEY)
//
// ============================================
// HOW JWT AUTH WORKS
// ============================================
//
// 1. User logs in → Server creates JWT token
// 2. Server sends token to client
// 3. Client stores token (localStorage)
// 4. Client sends token with every request: "Authorization: Bearer <token>"
// 5. Server validates token on each request
// 6. If valid → allow access, If invalid/expired → deny access
