package com.example.tasklist.web.security;

import java.security.Key;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.tasklist.domain.exception.AccessDeniedException;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.UserService;
import com.example.tasklist.service.props.JwtProperties;
import com.example.tasklist.web.dto.auth.JwtResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Service
public class JwtTokenProvider {
	
	private final JwtProperties jwtProperties;
	private final UserDetailsService userDetailsService;
	private final UserService userService;
	private Key key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
	}
 	
	@Autowired
	public JwtTokenProvider(JwtProperties jwtProperties, UserDetailsService userDetailsService,
			UserService userService) {
		this.jwtProperties = jwtProperties;
		this.userDetailsService = userDetailsService;
		this.userService = userService;
	}
	
	public String createAccessToken(Long id, String username, Set<Role> roles) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("id", id);
		claims.put("roles", resolveRoles(roles));
		Date now = new Date();
		Date validity = new Date(now.getTime() + jwtProperties.getAccess());
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(key)
				.compact();
	}
	
	private List<String> resolveRoles(Set<Role> roles){
		return roles.stream()
				.map(t -> t.name())
				.toList();
	}
	
	public String createRefreshToken(Long userId, String username) {
		Claims claims = Jwts.claims().setSubject(username);
		claims.put("id", userId);
		Date now = new Date();
		Date validity = Date.from(ZonedDateTime.now().plus(jwtProperties.getRefresh(), ChronoUnit.MILLIS).toInstant());
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(validity)
				.signWith(key)
				.compact();
	}
	
	public JwtResponse refreshUserTokens(String refreshToken) {
		JwtResponse jwtResponse = new JwtResponse();
		if(!validateToken(refreshToken)) {
			throw new AccessDeniedException(null);
		}
		Long userId = Long.valueOf(getId(refreshToken));
		User user = userService.getById(userId);
		jwtResponse.setId(userId);
		jwtResponse.setAccessToken(createAccessToken(user.getId(), user.getUsername(), user.getRoles()));
		jwtResponse.setRefreshToken(createRefreshToken(user.getId(), user.getUsername()));
		
		return jwtResponse;
	}

	public boolean validateToken(String token) {
		Jws<Claims> claims = Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
		return !claims.getBody().getExpiration().before(new Date());
	}
	
	private String getId(String token) {
		
		return Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.get("id")
				.toString();
	}
	
	private String getUsername(String token) {
		
		return Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public Authentication getAuthentication(String token) {
		String username = getUsername(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}
}
