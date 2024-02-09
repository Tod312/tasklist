package com.example.tasklist.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tasklist.domain.exception.ResourceNotFoundException;
import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.domain.user.User;
import com.example.tasklist.repository.TaskRepository;
import com.example.tasklist.repository.UserRepository;
import com.example.tasklist.service.TaskService;
import com.example.tasklist.service.UserService;

@Service
public class TaskServiceImpl implements TaskService{

	private final UserService userService;
	private final TaskRepository taskRepository;
	
	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository, UserService userService) {
		this.userService = userService;
		this.taskRepository = taskRepository;
	}

	
	@Override
	@Transactional(readOnly = true)
	@Cacheable(value = "TaskService::getById", key = "#id")
	public Task getById(Long id) {
		return taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Task not found"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Task> getAllByUserId(Long userId) {
		return taskRepository.findAllByUserId(userId);
	}

	@Override
	@Transactional
	@CachePut(value ="TaskService::getById", key = "#task.id")
	public Task update(Task task) {
		if(task.getStatus() == null)
			task.setStatus(Status.TODO);
		taskRepository.save(task);
		return task;
	}

	@Override
	@Transactional
	@Cacheable(value = "TaskService::getById", key = "#task.id")
	public Task create(Task task, Long userId) {
		task.setStatus(Status.TODO);
		User user = userService.getById(userId);
		user.getTasks().add(task);
		userService.update(user);
		return task;
	}

	@Override
	@Transactional
	@CacheEvict(value = "TaskService::getById", key = "#id")
	public void delete(Long id) {
		taskRepository.deleteById(id);
	}


	@Override
	@Transactional
	public void upload(Long id, TaskImage image) {
		// TODO Auto-generated method stub
		
	}

}
