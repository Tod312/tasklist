package com.example.tasklist.web.controller;

import java.util.List;

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
import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.web.dto.task.TaskDto;
import com.example.tasklist.web.dto.user.UserDto;
import com.example.tasklist.web.dto.validation.OnCreate;
import com.example.tasklist.web.dto.validation.OnUpdate;
import com.example.tasklist.web.mappers.TaskMapper;
import com.example.tasklist.web.mappers.UserMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

	private final UserService userService;
	private final TaskService taskService;
	
	private final UserMapper userMapper;
	private final TaskMapper taskMapper;
	
	
	public UserController(UserService userService, TaskService taskService, UserMapper userMapper,
			TaskMapper taskMapper) {
		this.userService = userService;
		this.taskService = taskService;
		this.userMapper = userMapper;
		this.taskMapper = taskMapper;
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get UserDto by id")
	@PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
	public UserDto getById(@PathVariable Long id) {
		User user = userService.getById(id);
		return userMapper.toDto(user);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update user")
	@PreAuthorize("@customSecurityExpression.canAccessUser(#dto)")
	public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
		User user = userMapper.toEntity(dto);
		User updateUser = userService.update(user);
		return userMapper.toDto(updateUser);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete user by id")
	@PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}
	
	@GetMapping("/{id}/users")
	@Operation(summary = "Get all User tasks")
	@PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
	public List<TaskDto> getTasksByUserId(@PathVariable Long id){
		List<Task> tasks = taskService.getAllByUserId(id);
		return taskMapper.toDto(tasks);
	}
	
	@PostMapping("/{id}/tasks")
	@Operation(summary = "Add task to user")
	@PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
	public TaskDto createTask(@PathVariable Long id,@Validated(OnCreate.class) @RequestBody TaskDto dto) {
		Task task = taskMapper.toEntity(dto);
		Task created = taskService.create(task, id);
		return taskMapper.toDto(created);
	}
}
