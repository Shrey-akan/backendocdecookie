package com.demo.oragejobsite.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
@Id
@Column(columnDefinition = "VARCHAR(36)") 
private String uid; // Use UUID for the UID field

@Column(unique = true, nullable = false)
private String userName;
   private String userFirstName;
   private String userLastName;
   private String userPassword;
   private String companyuser;
   private String websiteuser;
   private String userphone;
   private String usercountry;
   private String userstate;
   private String usercity;
   private boolean verified;
   private String profile;
   
public User() {
	super();
	// TODO Auto-generated constructor stub
}



public User(String uid, String userName, String userFirstName, String userLastName, String userPassword,
		String companyuser, String websiteuser, String userphone, String usercountry, String userstate, String usercity,
		boolean verified, String profile) {
	super();
	this.uid = uid;
	this.userName = userName;
	this.userFirstName = userFirstName;
	this.userLastName = userLastName;
	this.userPassword = userPassword;
	this.companyuser = companyuser;
	this.websiteuser = websiteuser;
	this.userphone = userphone;
	this.usercountry = usercountry;
	this.userstate = userstate;
	this.usercity = usercity;
	this.verified = verified;
	this.profile = profile;
}



public String getUid() {
	return uid;
}

public void setUid(String uid) {
	this.uid = uid;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getUserFirstName() {
	return userFirstName;
}
public void setUserFirstName(String userFirstName) {
	this.userFirstName = userFirstName;
}
public String getUserLastName() {
	return userLastName;
}
public void setUserLastName(String userLastName) {
	this.userLastName = userLastName;
}
public String getUserPassword() {
	return userPassword;
}
public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}
public String getCompanyuser() {
	return companyuser;
}
public void setCompanyuser(String companyuser) {
	this.companyuser = companyuser;
}
public String getWebsiteuser() {
	return websiteuser;
}
public void setWebsiteuser(String websiteuser) {
	this.websiteuser = websiteuser;
}
public String getUserphone() {
	return userphone;
}
public void setUserphone(String userphone) {
	this.userphone = userphone;
}
public String getUsercountry() {
	return usercountry;
}
public void setUsercountry(String usercountry) {
	this.usercountry = usercountry;
}
public String getUserstate() {
	return userstate;
}
public void setUserstate(String userstate) {
	this.userstate = userstate;
}
public String getUsercity() {
	return usercity;
}
public void setUsercity(String usercity) {
	this.usercity = usercity;
}



public boolean isVerified() {
	return verified;
}



public void setVerified(boolean verified) {
	this.verified = verified;
}



public String getProfile() {
	return profile;
}



public void setProfile(String profile) {
	this.profile = profile;
}


}

