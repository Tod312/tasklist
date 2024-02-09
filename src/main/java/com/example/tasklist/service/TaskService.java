package com.example.tasklist.service;


import java.util.List;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.task.TaskImage;

public interface TaskService {

	Task getById(Long id);
	List<Task> getAllByUserId(Long userId);
	Task update(Task task);
	Task create(Task task, Long userId);
	void delete(Long id);
	void upload(Long id, TaskImage image);
}
