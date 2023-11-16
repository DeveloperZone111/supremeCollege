
package com.swag.supreme.Repositary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swag.supreme.Entity.User;

@Repository
public interface UserInfoRepositary extends JpaRepository<User,Integer> {
	
	
	//Optional<User> findByName(String username);

	Optional<User> findByEmail(String username);
	//User findByEmail1(String username);
	   
	List<User> findByRoles(String Role);

	   
	   
	   

}

