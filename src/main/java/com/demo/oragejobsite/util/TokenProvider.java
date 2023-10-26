package com.demo.oragejobsite.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.SecureRandom;
import java.security.SignatureException;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Component;

@Component
public class TokenProvider {
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 15 * 24 * 60 * 60 * 1000; // 15 days in milliseconds
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 15 * 60 * 24 * 1000; // 15 minutes in milliseconds

    private SecretKey refreshTokenSecret; // Use SecretKey instead of a String

    public TokenProvider() {
        // Initialize the SecretKey with a random key
        this.refreshTokenSecret = generateRandomSecretKey();
    }

    private SecretKey generateRandomSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32]; // 32 bytes will be base64 encoded to 64 characters
        secureRandom.nextBytes(randomBytes);
        return Keys.hmacShaKeyFor(randomBytes); // Create SecretKey from randomBytes
    }

    public String generateRefreshToken(String username) {
        Date expiryDate = new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME);
        String tokenId = UUID.randomUUID().toString();

        return Jwts.builder()
            .setSubject(username)
            .setExpiration(expiryDate)
            .signWith(refreshTokenSecret, SignatureAlgorithm.HS256)
            .setId(tokenId)
            .compact();
    }

    public String validateAndExtractUsernameFromRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(refreshTokenSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();

            String username = claims.getSubject();
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String generateAccessToken(String uid) {
        Date expiryDate = new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
            .setSubject(uid)
            .setExpiration(expiryDate)
            .signWith(refreshTokenSecret, SignatureAlgorithm.HS256) // Use refreshTokenSecret for signing
            .compact();
    }

    public java.sql.Date getExpirationDateFromRefreshToken(String refreshToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(refreshTokenSecret)
                .build()
                .parseClaimsJws(refreshToken)
                .getBody();

            Date expirationDate = claims.getExpiration();

            if (expirationDate != null) {
                // Convert the expiration date to a SQL Date
                return new java.sql.Date(expirationDate.getTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public SecretKey getRefreshTokenSecret() {
        return refreshTokenSecret;
    }

    public void setRefreshTokenSecret(SecretKey refreshTokenSecret) {
        this.refreshTokenSecret = refreshTokenSecret;
    }

    // You can add more methods or setters as needed.
}


