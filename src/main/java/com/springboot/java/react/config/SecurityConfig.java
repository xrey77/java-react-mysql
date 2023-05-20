package com.springboot.java.react.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.springboot.java.react.jwtfilter.JwtAuthFilter;
import com.springboot.java.react.services.JwtUserDetailsService;
//import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = false, securedEnabled = true)
public class SecurityConfig {
	
	@Autowired
	private JwtAuthFilter jwtAuthFilter;
				
	    @Bean
		public UserDetailsService userDetailsService() {
	    	return new JwtUserDetailsService();
		}
	
 	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }	    	     	     	    
 	    
  	   @Bean
  	   public AuthenticationProvider authenticationProvider() {
  	    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
  	    	authenticationProvider.setUserDetailsService(userDetailsService());
  	    	authenticationProvider.setPasswordEncoder(passwordEncoder());
  	    	return authenticationProvider;
  	   }
 	    @SuppressWarnings("removal")
		@Bean
 	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			return http
					.cors().and()
					.authorizeHttpRequests()
 	    			.requestMatchers("/**",
 	    					"/users/**",
 	    					"/images/**",
 	    					"/products/**",
 	    					"/api-docs/**", 	    					
 	    			        "/swagger-resources",
 	    		            "/swagger-resources/**",
 	    		            "/configuration/ui",
 	    		            "/configuration/security",
 	    		            "/context-path/**",
 	    		            "/context-path/v3/**",
 	    		            "/swagger-ui.html",
 	    		            "/swagger-ui/index.html",
 	    		            "/webjars/**",
 	    		            "/v3/api-docs",
 	    		            "/v3/api-docs/**",
 	    		            "/api/public/**",
 	    		            "/api/public/authenticate",
 	    		            "/actuator/*",
 	    		            "/swagger-ui/**" 	    					 	    					
 	    					)
 	    			.permitAll()
 	    			.requestMatchers("/api/v1/products/**").permitAll()
 	    			.requestMatchers("/api/v1/productusers/**").permitAll()
 	    			.requestMatchers("/api/v1/productadmin/**").permitAll() 	    			
// 	    			.requestMatchers("/api/vi/users/**")
// 	    			.hasRole("ADMIN")
// 	                .anyRequest()
// 	    			.authenticated()
 	                .and()
 	                .sessionManagement()
 	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 	                
 	                .and()
 	                .logout()
 	                .logoutSuccessUrl("/logout")               
 	                .and()
 	                .authenticationProvider(authenticationProvider())
 	                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
 	                .build();
 	    }
 	    
 	   @Bean
 	   public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
 		   return config.getAuthenticationManager();
 	   }

// 	  MAP IMAGES FROM STATIC FOLDER
 	  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
 		    registry.addResourceHandler("/images/**","/users/**","/products/**","/qrcodes/**").addResourceLocations("file://" + System.getProperty("user.dir") + "/src/main/resources/static/images");
 	  } 	   	  	    	  
}
