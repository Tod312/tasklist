package com.example.tasklist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.tasklist.domain.task.Task;

//@Mapper
public interface TaskRepository extends JpaRepository<Task, Long>{

	@Query(value = """
			SELECT * FROM tasks t
			JOIN users_tasks ut ON ut.task_id = t.id
			WHERE ut.user_id = :userId
			""", nativeQuery = true)
	List<Task> findAllByUserId(@Param("userId")Long userId);
	
}
