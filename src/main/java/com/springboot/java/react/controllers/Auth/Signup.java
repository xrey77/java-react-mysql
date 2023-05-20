package com.springboot.java.react.controllers.Auth;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.java.react.entities.Users;
import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.JwtUserDetailsService;
import com.springboot.java.react.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Signup", description = "Account Registration")
@CrossOrigin(origins = "*")
public class Signup {
	
	@Autowired
    UserService userService;

	@Autowired
	JwtUserDetailsService userDetails;
		

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
	    
	  
		public String GenerateSecretKey() {
		 	    SecureRandom random = new SecureRandom();
		 	    byte[] bytes = new byte[20];
		 	    random.nextBytes(bytes);
		 	    Base32 base32 = new Base32();
		 	    return base32.encodeToString(bytes);
		 }


}
