package com.springboot.java.react.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.java.react.entities.Privilege;

public interface PrivilegeRepository  extends JpaRepository<Privilege, Integer> {

}
