package com.demo.oragejobsite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ApplyJob {
	@Id
	@Column(columnDefinition = "VARCHAR(36)") 
	private String juid; // Use UUID for the UID field
	private String juname;
	private String jumail;
	private String jucompny;
	private String jutitle;
	private String juresume;
	private String jurelocation;
	private String jueducation;
	private String juexperience;
	private String juexpinjava;
	private String juexpjsp;
	private String juinterviewdate;
	private String jujavavalid;
	private String jujobtitle;
	private String jucompanyname;
	private String description;
	private Long juphone;
	private String julastsal;
	private String juexpecsalary;
	private String empid;
	private String uid;
	private String profileupdate;
	
	
	public ApplyJob() {	
		super();
		// TODO Auto-generated constructor stub
	}

	
	


	public ApplyJob(String juid, String juname, String jumail, String jucompny, String jutitle, String juresume,
			String jurelocation, String jueducation, String juexperience, String juexpinjava, String juexpjsp,
			String juinterviewdate, String jujavavalid, String jujobtitle, String jucompanyname, String description,
			Long juphone, String julastsal, String juexpecsalary, String empid, String uid, String profileupdate) {
		super();
		this.juid = juid;
		this.juname = juname;
		this.jumail = jumail;
		this.jucompny = jucompny;
		this.jutitle = jutitle;
		this.juresume = juresume;
		this.jurelocation = jurelocation;
		this.jueducation = jueducation;
		this.juexperience = juexperience;
		this.juexpinjava = juexpinjava;
		this.juexpjsp = juexpjsp;
		this.juinterviewdate = juinterviewdate;
		this.jujavavalid = jujavavalid;
		this.jujobtitle = jujobtitle;
		this.jucompanyname = jucompanyname;
		this.description = description;
		this.juphone = juphone;
		this.julastsal = julastsal;
		this.juexpecsalary = juexpecsalary;
		this.empid = empid;
		this.uid = uid;
		this.profileupdate = profileupdate;
	}





	public String getJuid() {
		return juid;
	}


	public void setJuid(String juid) {
		this.juid = juid;
	}

	public String getJuname() {
		return juname;
	}
	public void setJuname(String juname) {
		this.juname = juname;
	}
	public String getJuresume() {
		return juresume;
	}
	public void setJuresume(String juresume) {
		this.juresume = juresume;
	}
	public String getJurelocation() {
		return jurelocation;
	}
	public void setJurelocation(String jurelocation) {
		this.jurelocation = jurelocation;
	}
	public String getJueducation() {
		return jueducation;
	}
	public void setJueducation(String jueducation) {
		this.jueducation = jueducation;
	}
	public String getJuexperience() {
		return juexperience;
	}
	public void setJuexperience(String juexperience) {
		this.juexperience = juexperience;
	}
	public String getJuexpinjava() {
		return juexpinjava;
	}
	public void setJuexpinjava(String juexpinjava) {
		this.juexpinjava = juexpinjava;
	}
	public String getJuexpjsp() {
		return juexpjsp;
	}
	public void setJuexpjsp(String juexpjsp) {
		this.juexpjsp = juexpjsp;
	}
	public String getJuinterviewdate() {
		return juinterviewdate;
	}
	public void setJuinterviewdate(String juinterviewdate) {
		this.juinterviewdate = juinterviewdate;
	}
	public String getJujavavalid() {
		return jujavavalid;
	}
	public void setJujavavalid(String jujavavalid) {
		this.jujavavalid = jujavavalid;
	}
	public String getJujobtitle() {
		return jujobtitle;
	}
	public void setJujobtitle(String jujobtitle) {
		this.jujobtitle = jujobtitle;
	}
	public String getJucompanyname() {
		return jucompanyname;
	}
	public void setJucompanyname(String jucompanyname) {
		this.jucompanyname = jucompanyname;
	}

	public String getJumail() {
		return jumail;
	}

	public void setJumail(String jumail) {
		this.jumail = jumail;
	}
	public String getJucompny() {
		return jucompny;
	}



	public void setJucompny(String jucompny) {
		this.jucompny = jucompny;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getJuphone() {
		return juphone;
	}

	public void setJuphone(Long juphone) {
		this.juphone = juphone;
	}

	public String getJulastsal() {
		return julastsal;
	}

	public void setJulastsal(String julastsal) {
		this.julastsal = julastsal;
	}

	public String getJuexpecsalary() {
		return juexpecsalary;
	}

	public void setJuexpecsalary(String juexpecsalary) {
		this.juexpecsalary = juexpecsalary;
	}

	public String getJutitle() {
		return jutitle;
	}

	public void setJutitle(String jutitle) {
		this.jutitle = jutitle;
	}

	public String getEmpid() {
		return empid;
	}



	public void setEmpid(String empid) {
		this.empid = empid;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}





	public String getProfileupdate() {
		return profileupdate;
	}





	public void setProfileupdate(String profileupdate) {
		this.profileupdate = profileupdate;
	}
	
	
}

