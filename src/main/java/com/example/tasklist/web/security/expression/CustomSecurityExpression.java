package com.example.tasklist.web.security.expression;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.security.JwtEntity;

@Service("customSecurityExpression")
public class CustomSecurityExpression {

	private final UserService service;

	public CustomSecurityExpression(UserService service) {
		this.service = service;
	}
	
	public boolean canAccessUser(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Object objectUser = authentication.getPrincipal();
		
		JwtEntity user = (JwtEntity) objectUser;
		Long userId = user.getId();
		
		return userId.equals(id) || hasAnyRole(authentication, Role.ROLE_ADMIN);
	}
	
	
	private boolean hasAnyRole(Authentication authentication, Role... roles) {
		for(Role role : roles) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
			if(authentication.getAuthorities().contains(authority)) {
				return true;
			}
		}
			return false;
	}
	
	public boolean canAccessTask(Long taskId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		JwtEntity user = (JwtEntity) authentication.getPrincipal();
		Long userId = user.getId();
		
		return service.isTaskOwner(userId, taskId);
		
	}
}
