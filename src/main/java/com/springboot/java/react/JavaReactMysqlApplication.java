package com.springboot.java.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

import com.springboot.java.react.services.FilesStorageService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.annotation.Resource;




@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Java-ReactJS API", version = "2.0", description = "Users Information"))
@SecurityScheme(name = "springboot.java.react", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class JavaReactMysqlApplication {

	@Resource
	FilesStorageService storageService;
	  
	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}		
	
	@Bean
	public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	}	
	
	public static void main(String[] args) {
		SpringApplication.run(JavaReactMysqlApplication.class, args);
	}

	public void run(String... arg) throws Exception {
//	    storageService.deleteAll();
	    storageService.init();
	}	

}
