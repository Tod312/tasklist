package com.example.tasklist.web.security;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;

public class JwtEntityFactory {

	public static JwtEntity create(User user) {
		return new JwtEntity(
				user.getId(),
				user.getUsername(),
				user.getName(),
				user.getPassword(),
				mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
				);
	}
	
	private static List<GrantedAuthority> mapToGrantedAuthorities(ArrayList<Role> arrayList){
		return arrayList.stream()
				.map(role -> role.name())
				.map(name -> new SimpleGrantedAuthority(name))
				.collect(Collectors.toList());
		
	}
}
