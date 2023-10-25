package com.demo.oragejobsite.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Blogs {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int blogId;
private String blogHead;
private String blogSubHead;
private String blogDescription;
@Lob
private byte[] blogImg;
public Blogs(int blogId, String blogHead, String blogSubHead, String blogDescription, byte[] blogImg) {
	super();
	this.blogId = blogId;
	this.blogHead = blogHead;
	this.blogSubHead = blogSubHead;
	this.blogDescription = blogDescription;
	this.blogImg = blogImg;
}
public Blogs() {
	super();
	// TODO Auto-generated constructor stub
}
public int getBlogId() {
	return blogId;
}
public void setBlogId(int blogId) {
	this.blogId = blogId;
}
public String getBlogHead() {
	return blogHead;
}
public void setBlogHead(String blogHead) {
	this.blogHead = blogHead;
}
public String getBlogSubHead() {
	return blogSubHead;
}
public void setBlogSubHead(String blogSubHead) {
	this.blogSubHead = blogSubHead;
}
public String getBlogDescription() {
	return blogDescription;
}
public void setBlogDescription(String blogDescription) {
	this.blogDescription = blogDescription;
}
public byte[] getBlogImg() {
	return blogImg;
}
public void setBlogImg(byte[] blogImg) {
	this.blogImg = blogImg;
}


}
