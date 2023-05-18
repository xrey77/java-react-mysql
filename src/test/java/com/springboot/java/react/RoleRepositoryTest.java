package com.springboot.java.react;

//import java.util.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.annotation.Rollback;
//import com.springboot.java.react.entities.Role;
//import com.springboot.java.react.repositories.RoleRepository;
//import org.springframework.boot.test.context.SpringBootTest;

//import jakarta.transaction.Transactional;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot.java.react.repositories.RoleRepository;

import org.junit.jupiter.api.extension.ExtendWith;

//@ExtendWith(SpringExtension.class)
//@Transactional
//@SpringBootTest(classes = JavaReactMysqlApplicationTests.class)
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RoleRepositoryTest {

	@Autowired private RoleRepository repo;
	
	@Test
	public void testSaveRole() {
//		Role supervisor = new Role("ROLE_SUPERVISOR");
//		Role editor = new Role("ROLE_EDITOR");
//		
//		repo.saveAll(List.of(supervisor, editor));		
		
//		assertEquals(3, 3);
        assertThat(repo).isNotNull();

	}
}
