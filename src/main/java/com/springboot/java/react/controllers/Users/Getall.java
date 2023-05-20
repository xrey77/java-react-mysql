package com.springboot.java.react.controllers.Users;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.FilesStorageService;
import com.springboot.java.react.services.JwtService;
import com.springboot.java.react.services.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Get All Users", description = "Listings of all users")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class Getall {
	
	@Autowired
	FilesStorageService storageService;
	 
	@Autowired
	private JwtService jwtService;
	
	@Autowired
    UserService userService;

	@RolesAllowed("ROLE_ADMIN")	
	@GetMapping(path="/getall")
    public Map<String, Object> list(HttpServletRequest req) {

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

//				CHECK IF EXTRACTED USERNAME IS EQUAL WITH THE USERNAME PARAMS
				if(!username.equals(uname)) {
					return Map.of("statuscode", 401, "message", "UnAuthorized Access.");					
				}				
			}
			
//			CHECKK IF JWT AUTHORIZATION IS EMPTY
			if (authHeader == null) {
				return Map.of("statuscode", 403, "message", "Forbidden Access.");				
			}    
						
//			LIST USERS
			List<UserDto> users = userService.listAllUsers();
			return Map.of("statuscode", 200, "users", users);			
	        
		} catch(Exception ex) {
			return Map.of("statuscode", 500, "message", "Internal Server Error.");			
		}	        
    }




	

}