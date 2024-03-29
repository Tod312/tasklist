package com.example.tasklist.web.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;


public class JwtEntity implements UserDetails{

	private Long id;
	private final String username;
	private final String name;
	private final String password;
	private final Collection<? extends GrantedAuthority> authorities;
	
	
	public JwtEntity(Long id, String username, String name, String password,
			Collection<? extends GrantedAuthority> authorities) {
		
		this.id = id;
		this.username = username;
		this.name = name;
		this.password = password;
		this.authorities = authorities;
	}

	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
