package com.springboot.java.react.controllers.Auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.UserService;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "TOTP Validation", description = "Timebased One Time Password")
@CrossOrigin(origins = "*")
public class ValidateOtp {
	
	@Autowired
    UserService userService;
	

    @PutMapping(path="/validateotpcode/{id}/{otp}")	
	public Map<String, Object> validateTotp(@PathVariable Integer id, @PathVariable String otp) {
    	try {
    	TimeProvider timeProvider = new SystemTimeProvider();
		CodeGenerator codeGenerator = new DefaultCodeGenerator();
		CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
		UserDto user = userService.getUser(id);
        if (user != null) {
    		String secret=user.getSecretkey();
    		boolean successful = verifier.isValidCode(secret, otp);
    		if(successful) {
    			return Map.of("statuscode", 200, "message", "Successfull OTP Code validation..","username", user.getUsername());			
    		}        	
        }
 		return Map.of("statuscode", 404, "message", "OTP Code is not valid..");			    	 
        
     } catch(Exception ex) {
 		return Map.of("statuscode", 500, "message", ex.getMessage());			    	 
     }
	}			
}
