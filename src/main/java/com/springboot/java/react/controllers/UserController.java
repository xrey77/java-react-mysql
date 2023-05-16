package com.springboot.java.react.controllers;

import dev.samstevens.totp.time.TimeProvider;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.io.*;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.FilesStorageService;
import com.springboot.java.react.services.JwtService;
import com.springboot.java.react.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import com.springboot.java.react.utils.ApiError;
import de.taimos.totp.TOTP;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import java.io.File;
import java.io.IOException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
public class UserController {
	
	@Autowired
	FilesStorageService storageService;
	 
	@Autowired
	private JwtService jwtService;
	
	@Autowired
    UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;
        
	@GetMapping(path="/getall")
    public ResponseEntity<?> list(HttpServletRequest req) {
		ApiError apiError = new ApiError();
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
				
//				CHECK IF USERNAME EXISTS
				if(username == null) {
			  		  apiError.setStatuscode("403");
			  		  apiError.setMessage("Forbidden Access.");				
			  		  return ResponseEntity.status(403).body(apiError);								
				}

//				CHECK IF EXTRACTED USERNAME IS EQUAL WITH THE USERNAME PARAMS
				if(!username.equals(uname)) {
			  		  apiError.setStatuscode("401");
			  		  apiError.setMessage("UnAuthorized Access.");				
			  		  return ResponseEntity.status(401).body(apiError);					
				}
				
			}
			
//			CHECKK IF JWT AUTHORIZATION IS EMPTY
			if (authHeader == null) {
		  		  apiError.setStatuscode("403");
		  		  apiError.setMessage("Forbidden Access.");				
		  		  return ResponseEntity.status(403).body(apiError);			
			}
						
//			LIST USERS
			List<UserDto> users = userService.listAllUsers();
	        return ResponseEntity.status(200).body(users);
	        
		} catch(Exception ex) {
			
	  		  apiError.setStatuscode("500");
	  		  apiError.setMessage("Internal Server Error.");				
	  		  return ResponseEntity.status(500).body(apiError);						
		}	        
    }

	@GetMapping(path = "/getuserbyid/{id}")
//***	@PreAuthorize("hasAuthority('ROLE_USER')")	
    public Map<String, Object> getuser(@PathVariable Integer id, HttpServletRequest req) {
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
			UserDto user = userService.getUser(id);
            user.setPassword(null);
  		    return Map.of("statuscode", 200,"message", "Profile loaded successfully.", "user",user);            
		}
		catch(Exception ex)
		{
  		    return Map.of("statuscode", 404, "message", "User not found.");
		}
    }	
	
    @PutMapping(path="/updateuserpassword/{id}")	
    public Map<String, Object> updateuser(@RequestBody UserDto user, HttpServletRequest req, @PathVariable Integer id) {
		String uname = (String) req.getSession().getAttribute("USERNAME");
		Calendar cal = Calendar.getInstance();
		try {
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
	
    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, HttpServletRequest req) {
		ApiError apiError = new ApiError();
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
				
//				CHECK IF USERNAME EXISTS
				if(username == null) {
			  		  apiError.setStatuscode("403");
			  		  apiError.setMessage("Forbidden Access.");				
			  		  return ResponseEntity.status(403).body(apiError);								
				}				
				
				if(!username.equals(uname)) {
			  		  apiError.setStatuscode("401");
			  		  apiError.setMessage("UnAuthorized Access.");				
			  		  return ResponseEntity.status(401).body(apiError);				
				}
				
			}
			
//			CHECKK IF JWT AUTHORIZATION IS EMPTY
			if (authHeader == null) {
		  		  apiError.setStatuscode("403");
		  		  apiError.setMessage("Forbidden Access.");				
		  		  return ResponseEntity.status(403).body(apiError);			
			}
						
		} catch(Exception ex) {
			
	  		  apiError.setStatuscode("500");
	  		  apiError.setMessage("Internal Server Error.");				
	  		  return ResponseEntity.status(500).body(apiError);						
		}
		
		try {
			userService.deleteUser(id);
			return ResponseEntity.status(200).body("Successfully deleted.");
		}
		catch(Exception ex) {
			ex.getStackTrace();
	        return ResponseEntity.status(404).body("User not found.");
		}
	}

    public void sendMail(String to, String subject, String msg) {
		SimpleMailMessage xmsg = new SimpleMailMessage();
        xmsg.setTo(to);
        xmsg.setSubject(subject);
        xmsg.setText(msg);
        javaMailSender.send(xmsg);
    }	
	
    @PutMapping(path="/uploaduserpicture/{id}")
    public ResponseEntity<?> uploadpicture(@RequestParam("userpic") MultipartFile file, @PathVariable Integer id) throws IOException  {

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
		ApiError apiError = new ApiError();
		apiError.setStatuscode("200");
		apiError.setMessage("ok.");				
		return ResponseEntity.status(200).body(apiError);			
		
	}	 		
	
// **	http://localhost:8085/api/v1/users/enabletotp/1	
// **	{
// **	    "emailadd": "reynald88@yahoo.com",
// **	    "isactivated": "0"
// **	}	
    @PutMapping(path="/activateotp/{id}")
    public Map<String, Object> enableAuthenticator(@RequestBody UserDto user, @PathVariable Integer id) {
		try {
			UserDto eUser = userService.getUser(id);
			if (eUser != null) {
			
				String secretkey=eUser.getSecretkey();			
				String issuer = "BARCLAYS BANK";
				String qrCodeURL = getGoogleAuthenticatorBarCode(secretkey, eUser.getEmailadd(), issuer);
				if(user.getIsactivated() == 1) {
	
					String newfile = "00" + id.toString() + ".png";
					File destination = new File("src/main/resources/static/qrcodes/" + newfile);
					
					createQRCode(qrCodeURL,destination.toString(), 200,200);
					
					String urlqrcode = "http://localhost:8085/qrcodes/" + newfile;
			        eUser.setQrcodeurl(urlqrcode);
			        userService.activateOTP(eUser);
				} else {
			        eUser.setQrcodeurl(null);
			        userService.activateOTP(eUser);			        
			        return Map.of("statuscode", 200, "message", "2-Factor Authentication deactivated.");			        
				}
		        return Map.of("statuscode", 200, "message", "2-Factor Authentication activated.");
			}
			return Map.of("statuscode", 404, "message", "User not found.");			  
			
    	}
		catch(Exception ex) {
			  return Map.of("statuscode", 500, "message", "Internal Server Error.");			  			
		}
    }
    
	   public static String getGoogleAuthenticatorBarCode(String secretKey, String account, String issuer) {
		    try {
		        return "otpauth://totp/"
		                + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
		                + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
		                + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
		    } catch (UnsupportedEncodingException e) {
		        throw new IllegalStateException(e);
		    }
		}
 	
	public void createQRCode(String barCodeData, String filePath, int height, int width)
	        throws WriterException, IOException {
	    BitMatrix matrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE,
	            width, height);
	    try (FileOutputStream out = new FileOutputStream(filePath)) {
	        MatrixToImageWriter.writeToStream(matrix, "png", out);
	    }
	}	

	public static String getTOTPCode(String secretKey) {
	    Base32 base32 = new Base32();
	    byte[] bytes = base32.decode(secretKey);
	    String hexKey = Hex.encodeHexString(bytes);
	    return TOTP.getOTP(hexKey);
	}	
        		  				
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