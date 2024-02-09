package com.example.tasklist.web.mappers;

import org.mapstruct.Mapper;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.web.dto.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto>{
}
