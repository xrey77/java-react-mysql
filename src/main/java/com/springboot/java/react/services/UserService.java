package com.springboot.java.react.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.java.react.entities.Users;
import com.springboot.java.react.models.dto.*;
import com.springboot.java.react.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
    private UserRepository userRepository;
	
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
        userDto.setRoles("ROLE_USER");
        userDto.setPicture("http://localhost:8085/images/user.jpg");
    	Users user = mapToEntity(userDto);
    	Users newUser = userRepository.save(user);
    	UserDto userResponse = mapToDto(newUser);
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

    
}
