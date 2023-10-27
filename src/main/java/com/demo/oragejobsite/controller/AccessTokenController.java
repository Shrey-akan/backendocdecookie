package com.demo.oragejobsite.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.web.bind.annotation.RestController;

import com.demo.oragejobsite.util.TokenProvider;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccessTokenController {
	
	@Value("${jwt.secret}")
	private String jwtSecretValue;
	
	// Inject the TokenProvider here
	private TokenProvider tokenProvider;

	public AccessTokenController(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	 
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/checkAccessTokenValidity")
	public ResponseEntity<String> checkAccessTokenValidity(@RequestBody Map<String, String> requestMap) {
	    try {
	        String accessToken = requestMap.get("accessToken");

	        if (tokenProvider.isAccessTokenValid(accessToken)) {
	        	return ResponseEntity.ok().body("{\"status\":\"Access token is valid.\"}");

	        } else {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token is invalid or has expired.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
	    }
	}

	
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/refreshToken")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> requestMap) {
	    try {
	        // Get the refreshToken value from the requestMap
	        String refreshToken = requestMap.get("refreshToken");

	        // Log the received refresh token
	        System.out.println("Received Refresh Token: " + refreshToken);

	        if (refreshToken == null || refreshToken.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
	        }

	        // Check if the refresh token is valid
	        if (tokenProvider.isRefreshTokenValid(refreshToken)) {
	            // Extract the username from the refresh token
	            String username = tokenProvider.validateAndExtractUsernameFromRefreshToken(refreshToken);

	            if (username != null) {
	                // Generate a new access token
	                String newAccessToken = tokenProvider.generateAccessToken(username);

	                // Return the new access token
	                Map<String, Object> responseBody = new HashMap<>();
	                responseBody.put("accessToken", newAccessToken);

	                return ResponseEntity.ok(responseBody);
	            }
	        }

	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
	    } catch (Exception e) {
	        e.printStackTrace(); // Log the exception for debugging
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

}
