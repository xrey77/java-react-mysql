package com.springboot.java.react.models;

//import java.sql.Timestamp;

public class UserModel {

    private int id;
    private String username;
    private String password;
    private int isactivated;
    private int isblocked;
    private String roles;
    private String lastname;
    private String firstname;
    private String emailadd;
    private String secretky;
    private String mobileno;
    private String qrcodeurl;    
    private String picture;
    private String token;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIsactivated() {
		return isactivated;
	}
	public void setIsactivated(int isactivated) {
		this.isactivated = isactivated;
	}
	public int getIsblocked() {
		return isblocked;
	}
	public void setIsblocked(int isblocked) {
		this.isblocked = isblocked;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getEmailadd() {
		return emailadd;
	}
	public void setEmailadd(String emailadd) {
		this.emailadd = emailadd;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public String getQrcodeurl() {
		return qrcodeurl;
	}
	public void setQrcodeurl(String qrcodeurl) {
		this.qrcodeurl = qrcodeurl;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSecretky() {
		return secretky;
	}
	public void setSecretky(String secretky) {
		this.secretky = secretky;
	}
	
}
