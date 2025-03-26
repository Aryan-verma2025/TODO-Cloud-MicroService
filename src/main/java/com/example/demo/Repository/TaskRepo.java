package com.example.demo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Task;
import com.example.demo.Types.TaskPriority;
import com.example.demo.Types.TaskStatus;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long>{

    @Query(value = "SELECT id, name, description, status, priority, due_date, username  FROM tasks WHERE username = :username", nativeQuery = true)
    List<Task> getAllTasks(@Param("username") String username);

    @Query("SELECT t FROM Task t WHERE t.username = :username " +
    "AND (:priority IS NULL OR t.priority = :priority) " +
    "AND (:status IS NULL OR t.status = :status)")
    List<Task> getTasks(@Param("priority") TaskPriority priority, @Param("status") TaskStatus status, @Param("username") String username);
}
