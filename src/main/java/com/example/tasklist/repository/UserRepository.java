package com.example.tasklist.repository;

import java.util.Optional;

//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.tasklist.domain.user.Role;
import com.example.tasklist.domain.user.User;

//@Mapper
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);

	@Query(value = """
			SELECT EXISTS( 
			SELECT 1 FROM users_tasks
			WHERE user_id = :userId
			AND task_id = :taskId 
			)
			""", nativeQuery = true)
	boolean isTaskOwner(@Param("userId")Long userId, @Param("taskId")Long taskId);
}
