package com.springboot.java.react.controllers.Users;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.FilesStorageService;
import com.springboot.java.react.services.JwtService;
import com.springboot.java.react.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Update User", description = "Update user profile")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class Update {
	
	@Autowired
	FilesStorageService storageService;
	 
	@Autowired
	private JwtService jwtService;
	
	@Autowired
    UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;	

	@RolesAllowed("ROLE_ADMIN")	    	
    @PutMapping(path="/updateuserpassword/{id}")	
    public Map<String, Object> updateuser(@RequestBody UserDto user, HttpServletRequest req, @PathVariable Integer id) {

    	if(req.getSession().getAttribute("ROLENAME") != "ROLE_ADMIN") {
		    return Map.of("statuscode", 403, "message", "UnAuthorized Access.");		  
		}
    	
		Calendar cal = Calendar.getInstance();
		try {
			String uname = (String) req.getSession().getAttribute("USERNAME");
			String authHeader = req.getHeader("Authorization");
			String token = null;
			String username = null;
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				
//				EXTRACT JWT
				token = authHeader.substring(7);
				
//				EXTRACT USERNAME FROM JWT
				username = jwtService.extractUsername(token);
				
//				CHECK IF USERNAME EXISTS
				if(username == null) {
			  		return Map.of("statuscode", 403, "message", "Forbidden Access.");	 			  		  					
				}
//				CHECK IF EXTRACTED USERNAME IS EQUAL WITH THE USERNAME PARAMS
				if(!username.equals(uname)) {
			  		return Map.of("statuscode", 401, "message", "UnAuthorized Access.");	 			  		  					
				}				
					
			}
			
//			CHECKK IF JWT AUTHORIZATION IS EMPTY
			if (authHeader == null) {
		  		return Map.of("statuscode", 403, "message", "Forbidden Access.");	 			  		  				
			}
			
		} catch(Exception ex) {
	  		return Map.of("statuscode", 500, "message", "Internal Server Error.");			
		}
						
		try {
			UserDto existUser = userService.getUser(id);
			existUser.setLastname(user.getLastname());
			existUser.setFirstname(user.getFirstname());
		    existUser.setMobileno(user.getMobileno());
		    if (user.getPassword() != null) {
		       existUser.setPassword(passwordEncoder.encode(user.getPassword()));
		    }
		    existUser.setUpdated_at(new Timestamp(cal.getTimeInMillis()));
		    userService.updateUsers(existUser);
	  		return Map.of("statuscode", 200, "message", "Profile successfully updated.");			
    	}
		catch(Exception ex) {
				System.out.println("May Error : " + ex.getMessage());
		  		return Map.of("statuscode", 404, "message", "User not found.");			
		}
    }		
}
