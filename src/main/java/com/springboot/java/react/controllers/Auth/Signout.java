package com.springboot.java.react.controllers.Auth;

import java.io.IOException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Sign Out", description = "Logout from application")
@CrossOrigin(origins = "*")
public class Signout {

	  @GetMapping(path = "/logout")
	  public void logout(HttpServletRequest req, HttpServletResponse response) throws IOException {
		  req.getSession().invalidate();
		  response.sendRedirect("/"); 
	  }	
	
}
