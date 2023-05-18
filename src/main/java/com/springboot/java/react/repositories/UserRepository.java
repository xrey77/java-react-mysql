package com.springboot.java.react.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.springboot.java.react.entities.Users;
import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {
	
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    public Optional<Users> getUserByUsername(@Param("username") String username);
	
    @Query("SELECT u FROM Users u WHERE u.emailadd = :emailadd")
    public Optional<Users> getUserByEmail(@Param("emailadd") String emailadd);

    @Query(value="DELETE FROM Users u WHERE u.id = :id")
    public int deleteByUser(@Param("id") int id);
    
    @Transactional
    @Modifying    
    @Query(value="UPDATE Users u SET u.firstname=?1, u.lastname=?2, u.mobileno=?3, u.password=?4 WHERE u.id = ?5")
    public int updateUser(String firstname, String lastname, String mobileno, String password, int id);

    @Transactional
    @Modifying    
    @Query(value="UPDATE Users u SET u.qrcodeurl=?1 WHERE u.id = ?2")
    public int activateMFA(String qrcodeurl, int id);
    
    @Transactional
    @Modifying    
    @Query(value="UPDATE Users u SET u.picture=?1 WHERE u.id = ?2")
    public int updateUserpic(String picture, int id);    
}
