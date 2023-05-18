package com.springboot.java.react.entities;

import java.sql.Timestamp;
import java.util.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Users")
public class Users implements UserDetails {

    @ManyToMany 
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
	private Collection<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
//		Users user = new Users();
//		for(Role role: user.getRoles()){
//			authorities.add(new SimpleGrantedAuthority(role.getName()));
//		}
		return authorities;
	}
    
    public Users() {}
	
	private static final long serialVersionUID = 5926468583005150707L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name="username")
    private String username;
    
    @Column(name="password")
    private String password;

    @Column(name="isactivated")
    private int isactivated;

    @Column(name="isblocked")
    private int isblocked;
                  
    @Column(name="lastname")
    private String lastname;

    @Column(name="firstname")
    private String firstname;
    
    @Column(name="emailadd")
    private String emailadd;
    
    @Column(name="mobileno")
    private String mobileno;
        
    @Column(name="qrcodeurl")
    private String qrcodeurl;    

    @Column(name="picture")
    private String picture;

    @Column(name="secretkey")
    private String secretkey;    
    
    @Column(name="mailtoken")
    private int mailtoken;
      
    @Column(name="updated_at")
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
	
	public int getMailtoken() {
		return mailtoken;
	}
	
	public void setMailtoken(int mailtoken) {
		this.mailtoken = mailtoken;
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
	
	public Timestamp getUpdated_at() {
		return updated_at;
	}
	
	public void setUpdated_at(Timestamp updated_at) {
		this.updated_at = updated_at;
	}
		
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	public String getSecretkey() {
		return secretkey;
	}
	
	public void setSecretkey(String secretkey) {
		this.secretkey = secretkey;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

  
}
