package com.example.tasklist.web.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.task.TaskImageDto;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskImageMapper;
import com.example.tasklist.web.mappers.TaskMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/tasks")
@Validated
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {

	private final TaskService taskService;
	
	private final TaskMapper taskMapper;
	private final TaskImageMapper imageMapper;	
	
	public TaskController(TaskService taskService, TaskMapper taskMapper, TaskImageMapper imageMapper) {
		this.taskService = taskService;
		this.taskMapper = taskMapper;
		this.imageMapper = imageMapper;
	}

	@PutMapping
	@Operation(summary = "Update task")
	@PreAuthorize("@customSecurityExpression.canAccessTask(#dto.id)")
	public TaskDto udpate(@Validated(OnUpdate.class) @RequestBody TaskDto dto) {
		Task task = taskMapper.toEntity(dto);
		Task updateTask = taskService.update(task);
		return taskMapper.toDto(updateTask);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get TaskDto by id")
	@PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
	public TaskDto getByid(@PathVariable Long id) {
		Task task = taskService.getById(id);
		return taskMapper.toDto(task);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete task")
	@PreAuthorize("@customSecurityExpression.canAccessTask(#dto.id)")
	public void deleteById(@PathVariable Long id) {
		taskService.delete(id);
	}
	
	@PostMapping("/{id}/image")
	@Operation(summary = "Upload image to task")
	@PreAuthorize("@customSecurityExpression.canAccessTask(#id)")
	public void uploadImage(@PathVariable Long id, TaskImageDto imageDto) {
		TaskImage image = imageMapper.toEntity(imageDto);
		taskService.uploadImage(id, image);
	}
	
}
