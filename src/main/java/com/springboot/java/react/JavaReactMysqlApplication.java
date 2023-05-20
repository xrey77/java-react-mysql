package com.springboot.java.react;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
import org.springframework.web.client.RestTemplate;

//import com.springboot.java.react.services.FilesStorageService;


@SpringBootApplication
public class JavaReactMysqlApplication {

//	@Resource
//	FilesStorageService storageService;
	  
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

//	public void run(String... arg) throws Exception {
//	    storageService.deleteAll();
//	    storageService.init();
//	}	

}
