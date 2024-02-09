package com.example.tasklist.web.mappers;

import java.util.List;

import org.mapstruct.Mapper;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.web.dto.task.TaskDto;

@Mapper(componentModel = "spring")
public interface TaskMapper extends Mappable<Task, TaskDto>{
}
