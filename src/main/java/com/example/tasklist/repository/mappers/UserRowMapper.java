package com.example.tasklist.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;

public class UserRowMapper {

	public static User mapRow(ResultSet resultSet) throws SQLException {
		Set<Role> roles = new HashSet<>();
		while(resultSet.next()) {
			roles.add(Role.valueOf(resultSet.getString("user_role")));
		}
		
		resultSet.beforeFirst();
		List<Task> tasks = TaskRowMapper.mapRows(resultSet);
		resultSet.beforeFirst();
		
		if(resultSet.next()) {
			User user = new User();
			user.setId(resultSet.getLong("user_id"));
			user.setName(resultSet.getString("user_name"));
			user.setUsername(resultSet.getString("user_username"));
			user.setPassword(resultSet.getString("user_password"));
			user.setRolse(roles);
			user.setTasks(tasks);
			return user;
		}
		return null;
		
	}
}
