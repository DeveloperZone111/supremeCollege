package com.swag.supreme.controller;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.security.access.prepost.PreAuthorize; 
import org.springframework.security.authentication.AuthenticationManager; 
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException; 
import org.springframework.web.bind.annotation.*;

import com.swag.supreme.Entity.AuthRequest;
import com.swag.supreme.Entity.AuthResponse;
import com.swag.supreme.Entity.User;
import com.swag.supreme.Repositary.UserInfoRepositary;
import com.swag.supreme.service.JwtService;
import com.swag.supreme.service.UserInfoService;


@RestController
//@CrossOrigin("*")
@RequestMapping("/auth") 
public class UserController { 
  
    @Autowired
    private UserInfoService service; 
  
    @Autowired
    private UserInfoRepositary repository; 
  
    @Autowired
    private JwtService jwtService; 
  
    @Autowired
    private AuthenticationManager authenticationManager; 
  
    @GetMapping("/welcome") 
    public String welcome() { 
        return "Welcome this endpoint is not secure"; 
    } 

	
	 @GetMapping(path="/getTeacherList")
	 public List<User> getTeacherList() {

	 return  repository.findByRoles("T");
	 
	 }
	 @GetMapping(path="/getStudentList")
	 public List<User> getStudentList() {

	 return  repository.findByRoles("S");
	 
	 }
  
    @PostMapping("/sinup") 
    public User addNewUser(@RequestBody User userInfo) { 
    	System.out.println("registration start");
    	System.out.print(userInfo.getPassword());
    	
        return service.addUser(userInfo); 
    } 
  
    @GetMapping("/user/userProfile") 
    @PreAuthorize("hasAuthority('ROLE_USER')") 
    public String userProfile() { 
        return "Welcome to User Profile"; 
    } 
  
    @GetMapping("/admin/adminProfile") 
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public String adminProfile() { 
        return "Welcome to Admin Profile"; 
    } 
  
    @PostMapping("/login") 
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) { 
    	
    	System.out.print(authRequest.getPassword());
    	System.out.print(authRequest.getUsername());
    	
    	
    	UsernamePasswordAuthenticationToken authRequest1 =new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
    	
        Authentication authentication = authenticationManager.authenticate(authRequest1); 
        if (authentication.isAuthenticated()) { 
            String token= jwtService.generateToken(authRequest.getUsername()); 
            UserDetails userDetails = service.loadUserByUsername(authRequest.getUsername());
            System.out.print(token);
            AuthResponse authresponse = new AuthResponse();
            authresponse.setUsername(jwtService.extractUsername(token));
            authresponse.setJwttoken(token);
            authresponse.setRole(userDetails.getAuthorities());
            System.out.print(jwtService.extractUsername(token)+"name from token");
         	System.out.print(jwtService.generateToken(authRequest.getUsername()));
             return authresponse;
        } else { 
            throw new UsernameNotFoundException("invalid user request !"); 
        } 
    } 
    
}

