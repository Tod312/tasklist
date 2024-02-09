package com.example.tasklist.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.AuthService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.dto.auth.JwtRequest;
import com.example.tasklist.web.dto.auth.JwtResponse;
import com.example.tasklist.web.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService{

	private final AuthenticationManager authenticationManager;
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	
	public AuthServiceImpl(AuthenticationManager authenticationManager, UserService userService,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public JwtResponse login(JwtRequest loginRequest) {
		User user2 = userService.getByUsername(loginRequest.getUsername());
		
		JwtResponse jwtResponse = new JwtResponse();	
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		User user = userService.getByUsername(loginRequest.getUsername());
		
		jwtResponse.setId(user.getId());
		jwtResponse.setUsername(user.getUsername());
		jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
		jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(user.getId(), user.getUsername()));
		return jwtResponse;
	}

	@Override
	public JwtResponse refresh(String refreshToken) {
		return jwtTokenProvider.refreshUserTokens(refreshToken);
	}

}
