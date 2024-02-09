package com.example.tasklist.web.security.expression;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.tasklist.domain.user.Role;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.security.JwtEntity;

import jakarta.servlet.http.HttpServletRequest;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations{

	private Object filterObject;
	private Object returnObject;
	private Object target;
	private HttpServletRequest request;
	
	private UserService userService;
	
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public CustomMethodSecurityExpressionRoot(Authentication authentication) {
		super(authentication);
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
		return userService.isTaskOwner(userId, taskId);
		
	}

	@Override
	public void setFilterObject(Object filterObject) {
		this.filterObject = filterObject;
		
	}

	@Override
	public Object getFilterObject() {
		return filterObject;
	}

	@Override
	public void setReturnObject(Object returnObject) {
		this.returnObject = returnObject;
	}

	@Override
	public Object getReturnObject() {
		return returnObject;
	}

	@Override
	public Object getThis() {
		return target;
	}

}
