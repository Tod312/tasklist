package com.example.tasklist.web.dto.task;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.example.tasklist.domain.task.Status;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


public class TaskDto {
	
	@NotNull(message = "Id must be not null.", groups = OnUpdate.class)
	private Long id;
	
	@NotNull(message = "Title must be not null.", groups = {OnUpdate.class, OnCreate.class})
	@Length(max = 255, message = "Title length must be smaller than 255 synbols", groups = {OnUpdate.class, OnCreate.class})
	private String title;
	
	@Length(max = 255, message = "Description length must be smaller than 255 synbols", groups = {OnUpdate.class, OnCreate.class})
	private String description;
	
	private Status status;
	
	@DateTimeFormat(iso = ISO.TIME)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime expirationDate;

	public TaskDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	@Override
	public String toString() {
		return "TaskDto [title=" + title + ", description=" + description + ", status=" + status + ", expirationDate="
				+ expirationDate + "]";
	}
	
	
}
