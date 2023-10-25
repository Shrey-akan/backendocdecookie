package com.demo.oragejobsite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DirectConntact {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int contactid;
private String name;
private String email;
private String contactNumber;
private String message;
public DirectConntact(int contactid, String name, String email, String contactNumber, String message) {
	super();
	this.contactid = contactid;
	this.name = name;
	this.email = email;
	this.contactNumber = contactNumber;
	this.message = message;
}
public DirectConntact() {
	super();
	// TODO Auto-generated constructor stub
}
public int getContactid() {
	return contactid;
}
public void setContactid(int contactid) {
	this.contactid = contactid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getContactNumber() {
	return contactNumber;
}
public void setContactNumber(String contactNumber) {
	this.contactNumber = contactNumber;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}




}
