package com.swag.supreme.Entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class AuthResponse
{

	private String username;
	
	private String jwttoken;
	private Collection<? extends GrantedAuthority> role;

	public Collection<? extends GrantedAuthority> getRole() {
		return role;
	}

	public void setRole(Collection<? extends GrantedAuthority> collection) {
		this.role = collection;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}
}
