package com.springboot.java.react.controllers;

import java.util.Optional;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.codec.binary.Base32;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.java.react.entities.Users;
import com.springboot.java.react.models.UserModel;
import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.JwtService;
//import com.springboot.java.react.services.JwtService;
import com.springboot.java.react.services.JwtUserDetailsService;
//import com.springboot.java.react.services.JwtUtils;
//import com.springboot.java.react.models.JwtResponse;
//import com.springboot.java.react.services.JwtUserDetailsService;
import com.springboot.java.react.services.UserService;
//import com.springboot.java.react.utils.ApiError;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

//import com.springboot.java.react.config.JwtTokenUtil;
//import com.springboot.java.react.models.JwtRequest;
import com.springboot.java.react.models.LoginModel;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth management APIs")
@CrossOrigin(origins = "*")
public class UserAuthController {
	
	@Autowired
    UserService userService;

	@Autowired
	JwtUserDetailsService userDetails;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;	
//	
	@Autowired
	private JwtService jwtService;
	
//	@Autowired
//	private JwtUtils jwtUtils;
	
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/signin")
	public Map<String, Object> UserLogin(@RequestBody LoginModel user, HttpSession session) {
		
		try {
          Optional<Users> xuser = userService.getUserName(user.getUsername());          
          if (xuser != null) {        	          	  
        	  
        	  if (xuser.get().getIsactivated() == 0) {
        		    return Map.of("statuscode", 403, "message","Please check your Email inbox, and activate your account.");
        	  }
//        	  Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        	  Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),new ArrayList<>()));
        	  
        	  if (authentication != null) {
//        	  if (passwordEncoder.matches(plainPwd, xuser.get().getPassword())) {

//        		  	String token = null;
        		    session.setAttribute("USERNAME", user.getUsername());
                  	final UserDetails userx = jwtUserDetailsService.loadUserByUsername(user.getUsername());
            		String token = jwtService.generateToken(userx.getUsername());
        		    UserModel model = new UserModel();
        			model.setId(xuser.get().getId());
        			model.setLastname(xuser.get().getLastname());
        			model.setFirstname(xuser.get().getFirstname());
        			model.setEmailadd(xuser.get().getEmailadd());
        			model.setMobileno(xuser.get().getMobileno());
        		    model.setQrcodeurl(xuser.get().getQrcodeurl());
        		    model.setPicture(xuser.get().getPicture());
        		    model.setUsername(xuser.get().getUsername());
        		    model.setRoles(xuser.get().getRoles());
        			model.setIsactivated(xuser.get().getIsactivated());
        			model.setIsblocked(xuser.get().getIsblocked());
        			model.setRoles(xuser.get().getRoles());
        			model.setPicture(xuser.get().getPicture());
        			model.setToken(token);
          		    return Map.of("statuscode", 200, "message", "Successfull login..", "user", model);
        	  } else {
        		    return Map.of("statuscode", 200, "message", "Password not valid.");
        	  }
          } else {
  		    return Map.of("statuscode", 404, "message", "User not found.");
          }
          
		} catch(Exception ex) {
		    return Map.of("statuscode", 404, "message", ex.getMessage());
		}
	}
	
	
  // http://localhost:8085/api/vi/auth/signup
  @PostMapping("/signup")
  public Map<String, Object> Register(@RequestBody UserDto user) {
  	  	
	try {
	    Optional<Users> existEmail = userService.getUserEmail(user.getEmailadd());
	    if (existEmail.get().getEmailadd() != null) {
	    	return Map.of("statuscode", 404, "message", "Email Address is taken.");
	    }
	} catch(Exception ex) {}
	
	try {
	  	Optional<Users> existUsername = userService.getUserName(user.getUsername());
	  	if (existUsername.get().getUsername() != null) {
	  		return Map.of("statuscode", 404, "message", "Username is taken");	  		
	  	}
	} catch(Exception ex) {}
  	  String secret = GenerateSecretKey();
  	  user.setSecretkey(secret);
      userService.addUser(user);  	
      return Map.of("statuscode", 200, "message","You have registered successfully.");      
  }	
    
  @GetMapping(path = "/logout")
  public String logout(HttpServletRequest req) {
	  req.getSession().invalidate();
	  return "redirect:/";
  }	
  
	public String GenerateSecretKey() {
	 	    SecureRandom random = new SecureRandom();
	 	    byte[] bytes = new byte[20];
	 	    random.nextBytes(bytes);
	 	    Base32 base32 = new Base32();
	 	    return base32.encodeToString(bytes);
	 }

	
}