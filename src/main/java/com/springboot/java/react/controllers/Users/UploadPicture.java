package com.springboot.java.react.controllers.Users;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.FilesStorageService;
import com.springboot.java.react.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Upload User Picture", description = "Upload and update user picture")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class UploadPicture {

	@Autowired
	FilesStorageService storageService;
	 	
	@Autowired
    UserService userService;	
	@RolesAllowed("ROLE_ADMIN")	    
    @PutMapping(path="/uploaduserpicture/{id}")
    public Map<String, Object> uploadpicture(@RequestParam("userpic") MultipartFile file, @PathVariable Integer id, HttpServletRequest req) throws IOException  {

    	if(req.getSession().getAttribute("ROLENAME") != "ROLE_ADMIN") {
		    return Map.of("statuscode", 403, "message", "UnAuthorized Access.");		  
		}

	    try {
	    	String newfile= "00" + id.toString() + ".jpeg";	    	
	        storageService.save(file, newfile);

			UserDto dto = userService.getUser(id);
			if(dto != null) {
				String urlimage = "http://localhost:8085/users/" + newfile;
				dto.setPicture(urlimage);
		        userService.updatePicture(dto);	        
			}
	      } catch (Exception e) {
	    	  System.out.println("May Error : " + e.getMessage());
	      }		
	    return Map.of("statuscode", 200, "message", "New picture uploaded successfully.");			
	}	 			
}
