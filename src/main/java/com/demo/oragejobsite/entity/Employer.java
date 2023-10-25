package com.demo.oragejobsite.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Employer {
	@Id
	@Column(columnDefinition = "VARCHAR(36)") 
	private String empid; // Use UUID for the UID field
	private String empfname;
	private String emplname;
	private String empcompany;
	@Column(unique = true, nullable = false)
	private String empmailid;
	private String emppass;
	private Long empphone;
	private String empcountry;
	private String empstate;
	private String empcity;
	private String descriptionemp;
	   private boolean verifiedemp;
	public Employer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Employer(String empid, String empfname, String emplname, String empcompany, String empmailid, String emppass,
			Long empphone, String empcountry, String empstate, String empcity, String descriptionemp,
			boolean verifiedemp) {
		super();
		this.empid = empid;
		this.empfname = empfname;
		this.emplname = emplname;
		this.empcompany = empcompany;
		this.empmailid = empmailid;
		this.emppass = emppass;
		this.empphone = empphone;
		this.empcountry = empcountry;
		this.empstate = empstate;
		this.empcity = empcity;
		this.descriptionemp = descriptionemp;
		this.verifiedemp = verifiedemp;
	}
	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	public boolean isVerifiedemp() {
		return verifiedemp;
	}



	public void setVerifiedemp(boolean verifiedemp) {
		this.verifiedemp = verifiedemp;
	}


	public String getEmpfname() {
		return empfname;
	}
	public void setEmpfname(String empfname) {
		this.empfname = empfname;
	}
	public String getEmplname() {
		return emplname;
	}
	public void setEmplname(String emplname) {
		this.emplname = emplname;
	}
	public String getEmpcompany() {
		return empcompany;
	}
	public void setEmpcompany(String empcompany) {
		this.empcompany = empcompany;
	}
	public String getEmpmailid() {
		return empmailid;
	}
	public void setEmpmailid(String empmailid) {
		this.empmailid = empmailid;
	}
	public String getEmppass() {
		return emppass;
	}
	public void setEmppass(String emppass) {
		this.emppass = emppass;
	}
	public Long getEmpphone() {
		return empphone;
	}
	public void setEmpphone(Long empphone) {
		this.empphone = empphone;
	}
	public String getEmpcountry() {
		return empcountry;
	}
	public void setEmpcountry(String empcountry) {
		this.empcountry = empcountry;
	}
	public String getEmpstate() {
		return empstate;
	}
	public void setEmpstate(String empstate) {
		this.empstate = empstate;
	}
	public String getEmpcity() {
		return empcity;
	}
	public void setEmpcity(String empcity) {
		this.empcity = empcity;
	}
	public String getDescriptionemp() {
		return descriptionemp;
	}
	public void setDescriptionemp(String descriptionemp) {
		this.descriptionemp = descriptionemp;
	}
	
	
}

