package com.swag.supreme.Repositary;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositary extends JpaRepository<com.swag.supreme.Entity.User,Integer> {
	
	   Optional<com.swag.supreme.Entity.User> findByName(String username);

}
