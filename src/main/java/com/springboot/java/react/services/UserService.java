package com.springboot.java.react.services;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.java.react.entities.Role;
import com.springboot.java.react.entities.Users;
import com.springboot.java.react.models.dto.*;
import com.springboot.java.react.repositories.RoleRepository;
import com.springboot.java.react.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	
    <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
          .stream()
          .map(element -> modelMapper.map(element, targetClass))
          .collect(Collectors.toList());
    }    
    
    public List<UserDto> listAllUsers() {
      List<Users> users = userRepository.findAll();
      List<UserDto> userDtoList = mapList(users, UserDto.class);      
      return userDtoList;
    }
        
    public UserDto addUser(UserDto userDto) {    	
    	userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role == null) {
        	checkRoleExist("ROLE_ADMIN");
        } else {
        	checkRoleExist("ROLE_USER");        	
        }
        userDto.setRoles(Arrays.asList(role));    	
        userDto.setPicture("http://localhost:8085/images/user.jpg");
    	Users user = mapToEntity(userDto);
    	Users newUser = userRepository.save(user);

    	UserDto userResponse = mapToDto(newUser);
    	
	    userDto.setRoles(Arrays.asList(role));    	
	  	userRepository.save(user);
    	
    	return userResponse;    	
    }    
    
    public Boolean updateUsers(UserDto userDto) {    	
    	userRepository.updateUser(userDto.getFirstname(), 
    			userDto.getLastname(), userDto.getMobileno(), userDto.getPassword(), 
    			userDto.getId());
    	return true;    	
    }   
    
    public Boolean updatePicture(UserDto dto) {
    	userRepository.updateUserpic(dto.getPicture(), dto.getId());
    	return true;
    }
            
    public UserDto getUser(Integer id) {
    	Users users = userRepository.findById(id).get();
    	UserDto userdto = mapToDto(users);
        return userdto;        
    }    

    public Boolean activateOTP(UserDto dto) {
    	userRepository.activateMFA(dto.getQrcodeurl(), dto.getId());
    	return true;
    }
    
    
    public Optional<Users> getUserName(String username) {
    	return userRepository.getUserByUsername(username);
    }
    
    public Optional<Users> getUserEmail(String emailadd) {
    	return userRepository.getUserByEmail(emailadd);
    }

    
    public Boolean deleteUser(Integer id) {
       userRepository.deleteByUser(id);
       return true;
    }
    
    private UserDto mapToDto(Users user) {
    	UserDto userDto = modelMapper.map(user, UserDto.class);
    	return userDto;
    }
    
    private Users mapToEntity(UserDto userDto) {
    	Users users = modelMapper.map(userDto, Users.class);
    	return users;
    }
    
//    public Optional<Role> getUserRoles(int id){
//    	Optional<Role> role = roleRepository.getUserrolename(id);
//    	   return role;
//    	}    

    private void checkRoleExist(String rolename){
//    	Optional<Users> user = userRepository.getUserByUsername(username);    	
//    	if(user != null) {
    		Role data = new Role();
//    		data.setId(user.get().getId());
    	    data.setName(rolename);
    	    roleRepository.save(data);
//    	    return true;    	        
//    	}
//    	return false;
    }    
}
