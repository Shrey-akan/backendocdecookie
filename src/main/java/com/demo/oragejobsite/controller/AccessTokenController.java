package com.demo.oragejobsite.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccessTokenController {

    @PostMapping("/checkAccessTokenValidity")
    public ResponseEntity<String> checkAccessTokenValidity(@RequestBody String accessToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey("jjbjhgbkgigcuol6354623g23c4y2t42werfd347637648c472i34723847823x4y378i78378943k4iyh23c4847y6238c4y6i") // Replace with your actual secret key
                .build()
                .parseClaimsJws(accessToken)
                .getBody();

            // Check if the token has not expired
            if (!claims.getExpiration().before(new Date())) {
                // Token is valid, return a 200 OK response
                return ResponseEntity.ok("Access token is valid.");
            } else {
                // Token is expired, return a 401 Unauthorized response
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token is expired.");
            }
        } catch (JwtException e) {
            // Token is invalid or there was an error parsing it
            // Return a 401 Unauthorized response or other appropriate error response
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Access token is invalid.");
        }
    }
}
