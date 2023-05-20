package com.springboot.java.react.controllers.Auth;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.springboot.java.react.entities.Role;
import com.springboot.java.react.entities.Users;
import com.springboot.java.react.models.UserModel;
import com.springboot.java.react.repositories.RoleRepository;
import com.springboot.java.react.services.JwtService;
import com.springboot.java.react.services.JwtUserDetailsService;
import com.springboot.java.react.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import com.springboot.java.react.models.LoginModel;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Signin", description = "Authentication and Authorization")
@CrossOrigin(origins = "*")
public class Signin {
	
	@Autowired
    UserService userService;

	@Autowired
	JwtUserDetailsService userDetails;
	
	@Autowired
	private JwtService jwtService;
		
//	@Autowired
//	private JwtUserDetailsService jwtUserDetailsService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	@Autowired
	private PasswordEncoder passwordEncoder;

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
        	  Authentication authentication = authenticationManager.authenticate(
        			  new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(),new ArrayList<>()));
        	  
        	  if (authentication.isAuthenticated()) {

//        	  if (passwordEncoder.matches(plainPwd, xuser.get().getPassword())) {
//        		  	String token = null;
        		  SecurityContext context = SecurityContextHolder.createEmptyContext(); 
        		  context.setAuthentication(authentication); 
        		  SecurityContextHolder.setContext(context);
        		    session.removeAttribute("USERNAME");
        		    session.setAttribute("USERNAME", user.getUsername());
        		    
          		    Role rolename = roleRepository.findById(xuser.get().getId());
      		    String userrole = rolename.getName();
      		    session.setAttribute("ROLENAME", userrole);
          		    
//          		    String userrole = rolename.getName().substring(5);
        		    
//                  	final UserDetails userx = jwtUserDetailsService.loadUserByUsername(user.getUsername());
                  	String pwd = passwordEncoder.encode(user.getPassword());
         	    	UserDetails userx = User.builder()
     	    		.username(user.getUsername())
     	    		.password(pwd)
     	    		.roles("ADMIN")
     	    		.build();                  	
                  	         	    	
            		String token = jwtService.generateToken(userx.getUsername());
//            		System.out.println("TOKEN : " + token);
        		    UserModel model = new UserModel();
        			model.setId(xuser.get().getId());
        			model.setLastname(xuser.get().getLastname());
        			model.setFirstname(xuser.get().getFirstname());
        			model.setEmailadd(xuser.get().getEmailadd());
        			model.setMobileno(xuser.get().getMobileno());
        		    model.setQrcodeurl(xuser.get().getQrcodeurl());
        		    model.setPicture(xuser.get().getPicture());
        		    model.setUsername(xuser.get().getUsername());
        			model.setIsactivated(xuser.get().getIsactivated());
        			model.setIsblocked(xuser.get().getIsblocked());
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
	
	
 
	
}