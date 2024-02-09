package com.example.tasklist.web.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Schema(description = "Request for login")
public class JwtRequest {

	@Schema(description = "email", example = "johndoe@gmail.com")
	@NotNull(message = "Username must be not null.")
	private String username;
	
	@Schema(description = "password", example = "12345")
	@NotNull(message = "Password must be not null.")
	private String password;

	
	public JwtRequest() {
	}

	public JwtRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
