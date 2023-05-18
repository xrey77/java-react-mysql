package com.springboot.java.react.models.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.springboot.java.react.entities.Role;

public class UserDto {

    private int id;
    private String username;
    private String password;
    private List<Role> roles = new ArrayList<>();
    
    private int isactivated;
    private int isblocked;
    private String lastname;
    private String firstname;
    private String emailadd;
    private String mobileno;
    private String qrcodeurl;    
    private String picture;
    private int mailtoken;
    private String token;
    private String secretkey;
    private Timestamp updated_at;
    
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
	public int getMailtoken() {
		return mailtoken;
	}
	public void setMailtoken(int mailtoken) {
		this.mailtoken = mailtoken;
	}
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSecretkey() {
		return secretkey;
	}
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
