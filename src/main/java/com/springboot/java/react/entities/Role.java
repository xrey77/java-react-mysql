package com.springboot.java.react.entities;

import java.util.Collection;

import jakarta.persistence.*;

@Entity
@Table(name = "Roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;
	    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
    @ManyToMany(mappedBy = "roles")
    private Collection<Users> users;
	
}
