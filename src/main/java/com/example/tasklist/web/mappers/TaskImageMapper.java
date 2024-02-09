package com.example.tasklist.web.mappers;

import org.mapstruct.Mapper;

import com.example.tasklist.domain.task.TaskImage;
import com.example.tasklist.web.dto.task.TaskImageDto;

@Mapper(componentModel = "spring")
public interface TaskImageMapper extends Mappable<TaskImage, TaskImageDto>{

}
