package com.springboot.java.react.repositories;

import com.springboot.java.react.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

//    @Query("SELECT u.name FROM Roles u WHERE u.id = ?1")
//    public Optional<Role> getUserrolename(int id);
    
	Role findByName(String name); 
}