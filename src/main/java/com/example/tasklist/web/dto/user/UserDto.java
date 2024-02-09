package com.example.tasklist.web.dto.user;

import org.hibernate.validator.constraints.Length;

import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "User DTO")
public class UserDto {
	
	@Schema(description = "User id", example = "1")
	@NotNull(message = "I must be not null.", groups = OnUpdate.class)
	private Long id;
	
	@Schema(description = "User name", example = "Jonh Doe")
	@NotNull(message = "Name must be not null.", groups = {OnUpdate.class, OnCreate.class})
	@Length(max = 255, message = "Name length must be smaller than 255 synbols", groups = {OnUpdate.class, OnCreate.class})
	private String name;
	
	@Schema(description = "User email", example = "johndoe@gmail.com")
	@NotNull(message = "Username must be not null.", groups = {OnUpdate.class, OnCreate.class})
	@Length(max = 255, message = "Username length must be smaller than 255 synbols", groups = {OnUpdate.class, OnCreate.class})
	private String username;
	
	@Schema(description = "user crypted password", example = "12345")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "Password must be not null.",  groups = {OnUpdate.class, OnCreate.class})
	private String password;
	
	@Schema(description ="user confirmation", example = "12345")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "Password confirmation must be not null.",  groups = {OnCreate.class})
	private String passwordConfirmation;

	public UserDto() {
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

	public void setName(String name) {
		this.name = name;
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

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}
	
	
}

