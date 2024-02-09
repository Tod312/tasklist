package com.example.tasklist.web.dto.task;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;

public class TaskImageDto {
	
	@NotNull(message = "Image must be not null")
	private MultipartFile file;
	
	
}
