package com.demo.oragejobsite.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PostJob {
@Id

@Column(columnDefinition = "VARCHAR(36)") 
private String jobid; // Use UUID for the UID field
private String jobtitle;
private String companyforthisjob;
private Long numberofopening;
private String locationjob;
private String jobtype;
private String schedulejob;
private Long payjob;
private Long payjobsup;
private String descriptiondata;
private String empid;

public PostJob() {
	super();
	// TODO Auto-generated constructor stub
}








public PostJob(String jobid, String jobtitle, String companyforthisjob, Long numberofopening, String locationjob,
		String jobtype, String schedulejob, Long payjob, Long payjobsup, String descriptiondata, String empid) {
	super();
	this.jobid = jobid;
	this.jobtitle = jobtitle;
	this.companyforthisjob = companyforthisjob;
	this.numberofopening = numberofopening;
	this.locationjob = locationjob;
	this.jobtype = jobtype;
	this.schedulejob = schedulejob;
	this.payjob = payjob;
	this.payjobsup = payjobsup;
	this.descriptiondata = descriptiondata;
	this.empid = empid;
}








public String getJobid() {
	return jobid;
}




public void setJobid(String jobid) {
	this.jobid = jobid;
}




public String getJobtitle() {
	return jobtitle;
}
public void setJobtitle(String jobtitle) {
	this.jobtitle = jobtitle;
}
public String getCompanyforthisjob() {
	return companyforthisjob;
}
public void setCompanyforthisjob(String companyforthisjob) {
	this.companyforthisjob = companyforthisjob;
}
public Long getNumberofopening() {
	return numberofopening;
}
public void setNumberofopening(Long numberofopening) {
	this.numberofopening = numberofopening;
}
public String getLocationjob() {
	return locationjob;
}
public void setLocationjob(String locationjob) {
	this.locationjob = locationjob;
}
public String getJobtype() {
	return jobtype;
}
public void setJobtype(String jobtype) {
	this.jobtype = jobtype;
}
public String getSchedulejob() {
	return schedulejob;
}
public void setSchedulejob(String schedulejob) {
	this.schedulejob = schedulejob;
}
public Long getPayjob() {
	return payjob;
}
public void setPayjob(Long payjob) {
	this.payjob = payjob;
}
public Long getPayjobsup() {
	return payjobsup;
}
public void setPayjobsup(Long payjobsup) {
	this.payjobsup = payjobsup;
}
public String getDescriptiondata() {
	return descriptiondata;
}
public void setDescriptiondata(String descriptiondata) {
	this.descriptiondata = descriptiondata;
}




public String getEmpid() {
	return empid;
}


public void setEmpid(String empid) {
	this.empid = empid;
}








}

