package com.demo.oragejobsite.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenId;
    private String username;
    private Date expiryDate;
    
   
	public RefreshToken() {
		super();
		// TODO Auto-generated constructor stub
	}
	public RefreshToken(Long id, String tokenId, String username, Date expiryDate) {
		super();
		this.id = id;
		this.tokenId = tokenId;
		this.username = username;
		this.expiryDate = expiryDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

    // Constructors, getters, setters
    
    
    
}

