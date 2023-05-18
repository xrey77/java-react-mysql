package com.springboot.java.react.services;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.springboot.java.react.config.UserInfoDetails;
import com.springboot.java.react.entities.Users;
import com.springboot.java.react.repositories.UserRepository;

@Component
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<Users> users = repository.getUserByUsername(username);
        return users.map(UserInfoDetails::new)
		.orElseThrow(()->new UsernameNotFoundException("Username not found."));
		
	}
	
}

