package com.springboot.java.react.controllers.Users;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

@Tag(name = "Get User ID", description = "Get user by id")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class Getid {

	@Autowired
	FilesStorageService storageService;
	 
	@Autowired
	private JwtService jwtService;
	
	@Autowired
    UserService userService;

	@RolesAllowed("ROLE_ADMIN")	
	@GetMapping(path = "/getuserbyid/{id}")
	public Map<String, Object> getuser(@PathVariable Integer id, HttpServletRequest req) {

//		if(req.getSession().getAttribute("ROLENAME") != "ROLE_ADMIN") {
//		    return Map.of("statuscode", 403, "message", "UnAuthorized Access.");		  
//	  }
		
	  try {
		String uname = (String) req.getSession().getAttribute("USERNAME");		
		try {
			String authHeader = req.getHeader("Authorization");
			String token = null;
			String username = null;

			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				
//				EXTRACT JWT
				token = authHeader.substring(7);
				
//				EXTRACT USERNAME FROM JWT
				username = jwtService.extractUsername(token);
				System.out.println("TOKEN : " + username);
				
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
			else if (authHeader == null) {
	  		    return Map.of("statuscode", 403, "message", "Forbidden Access.");
			}
			
		} catch(Exception ex) {
		    return Map.of("statuscode", 500, "message", "Internal Server Error.");			
		}

		try {
			UserDto user = userService.getUser(id);
          user.setPassword(null);
		    return Map.of("statuscode", 200,"message", "Profile loaded successfully.", "user",user);            
		}
		catch(Exception ex)
		{
		    return Map.of("statuscode", 404, "message", "User not found.");
		}

	  }catch(Exception ex) {
		    return Map.of("statuscode", 404, "message", ex.getMessage());		  
	  }
  }		
}
