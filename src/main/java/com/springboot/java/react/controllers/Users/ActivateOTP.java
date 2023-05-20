package com.springboot.java.react.controllers.Users;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.springboot.java.react.models.dto.UserDto;
import com.springboot.java.react.services.FilesStorageService;
import com.springboot.java.react.services.UserService;

import de.taimos.totp.TOTP;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Activate TOTP", description = "Activate/Deactivate 2-Factor Authenticator")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")
public class ActivateOTP {
	@Autowired
	FilesStorageService storageService;
	 	
	@Autowired
    UserService userService;
	

	// **	http://localhost:8085/api/v1/users/enabletotp/1	
	// **	{
	// **	    "emailadd": "reynald88@yahoo.com",
	// **	    "isactivated": "0"
	// **	}	
		@RolesAllowed("ROLE_ADMIN")	    
	    @PutMapping(path="/activateotp/{id}")
	    public Map<String, Object> enableAuthenticator(@RequestBody UserDto user, @PathVariable Integer id, HttpServletRequest req) {
	    	
			if(req.getSession().getAttribute("ROLENAME") != "ROLE_ADMIN") {
			    return Map.of("statuscode", 403, "message", "UnAuthorized Access.");		  
			}
	    	
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
	        		  					
}
