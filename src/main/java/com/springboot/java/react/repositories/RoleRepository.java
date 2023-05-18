package com.springboot.java.react.repositories;

import com.springboot.java.react.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value="SELECT r FROM roles r WHERE r.id = ?1 AND r.name = ?2",nativeQuery = true)
    Role getUserrolename(int id, String name);

    Role findById(int id);
	Role findByName(String name); 
}