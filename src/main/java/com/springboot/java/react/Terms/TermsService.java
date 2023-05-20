package com.springboot.java.react.Terms;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;

@Hidden
@Tag(name = "Terms of Service", description = "Application Terms of service")
@RestController
public class TermsService {

	@GetMapping(path="/terms")
	public String Terms() {
		String terms = "<h1>Terms of Service</h1>" + 
	"<p>- As the Payment service will no longer be available from 7 March 2023, we have amended Part B to include the closing date.</p>" + 
				"<p>- As the Siri Payments feature will no longer be available from 7 March 2023, we have amended Part B to include the closing date.</p>"
				+ "<p>For the purpose of these terms and conditions, ‘app’ shall refer to both the Barclays app and the Barclaycard app. References to ‘Barclays Mobile Banking’ in any communications, your agreements, marketing material or other documentation shall mean the app.</p>";
		
		return terms;
	}
	
}
