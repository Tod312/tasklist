package com.example.tasklist.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.UserService;

@Service
public class JwtUserDetailService implements UserDetailsService{

	private final UserService service;
	
	@Autowired
	public JwtUserDetailService(UserService service) {
		this.service = service;
	}

	@Override
	public UserDetails loadUserByUsername(String username)  {
		User user = service.getByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException("Username : " + username + "not found");
		return JwtEntityFactory.create(user);
	}

}
