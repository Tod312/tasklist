package com.example.tasklist.repository.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.example.tasklist.domain.task.Status;
import com.example.tasklist.domain.task.Task;

public class TaskRowMapper {

	public static Task mapRow(ResultSet resultSet) throws SQLException {
		if(resultSet.next()) {
			Task task = new Task();
			task.setId(resultSet.getLong("task_id"));
			task.setTitle(resultSet.getString("task_title"));
			task.setDescription(resultSet.getString("task_description"));
			task.setStatus(Status.valueOf(resultSet.getString("task_status")));
			Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
			if(timestamp != null) {
				task.setExpirationDate(timestamp.toLocalDateTime());
			}
			return task;
		}
		return null;
	}

	public static List<Task> mapRows(ResultSet resultSet) throws SQLException {
		List<Task> tasks = new ArrayList<Task>(resultSet.getFetchSize());
		while(resultSet.next()) {
			Task task = new Task();
			task.setId(resultSet.getLong("task_id"));
			if(!resultSet.wasNull()) {
				task.setTitle(resultSet.getString("task_title"));
				task.setDescription(resultSet.getString("task_description"));
				task.setStatus(Status.valueOf(resultSet.getString("task_status")));
				Timestamp timestamp = resultSet.getTimestamp("task_expiration_date");
				if(timestamp != null) {
					task.setExpirationDate(timestamp.toLocalDateTime());
				}
				tasks.add(task);
			}
		}
		return tasks;
	}
}
