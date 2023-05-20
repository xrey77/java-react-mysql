package com.springboot.java.react.controllers.Users;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.java.react.services.FilesStorageService;
import com.springboot.java.react.services.JwtService;
import com.springboot.java.react.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Delete User", description = "Delete user from database")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class Delete {

	@Autowired
	FilesStorageService storageService;
	 
	@Autowired
	private JwtService jwtService;
	
	@Autowired
    UserService userService;
	
	@RolesAllowed("ROLE_ADMIN")	    	
    @DeleteMapping("/deleteuser/{id}")
    public Map<String, Object> delete(@PathVariable Integer id, HttpServletRequest req) {

    	if(req.getSession().getAttribute("ROLENAME") != "ROLE_ADMIN") {
		    return Map.of("statuscode", 403, "message", "UnAuthorized Access.");		  
		}
    	
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
			userService.deleteUser(id);
			return Map.of("statuscode", 200, "message", "Successfully deleted.");				
		}
		catch(Exception ex) {
			ex.getStackTrace();
			return Map.of("statuscode", 404, "message", "User not found.");				
		}
	}
	
}
