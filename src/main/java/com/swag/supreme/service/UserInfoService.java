package com.swag.supreme.service;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.core.userdetails.UserDetailsService; 
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.security.crypto.password.PasswordEncoder; 
import org.springframework.stereotype.Service;

import com.swag.supreme.Entity.User;
import com.swag.supreme.Repositary.UserInfoRepositary;

import java.util.List;
import java.util.Optional; 
  
@Service
public class UserInfoService implements UserDetailsService { 
  
    @Autowired
    private UserInfoRepositary repository; 
  
    @Autowired
    private PasswordEncoder encoder; 
  
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
  
        Optional<User> userDetail = repository.findByEmail(username); 
  
        // Converting userDetail to UserDetails 
        return userDetail.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    } 
  
    public User addUser(User userInfo) { 
        userInfo.setPassword(encoder.encode(userInfo.getPassword())); 
        repository.save(userInfo); 
        return userInfo;
    }
    
 
    
    
	/*
	 * public boolean IsExistingUser(User userInfo) {
	 * 
	 * return true; }
	 */
  
  
} 
