package com.example.demo.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.Entities.Task;
import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<Task,Long>{

    @Query(value = "SELECT id, name, description, status, priority, due_date, username  FROM tasks WHERE username = :username", nativeQuery = true)
    List<Task> getAllTasks(@Param("username") String username);

}
