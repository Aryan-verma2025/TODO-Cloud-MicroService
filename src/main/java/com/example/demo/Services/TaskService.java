package com.example.demo.Services;


import java.util.List;

import com.example.demo.Entities.Task;

public interface TaskService{

    public Task createTask(Task task)throws IllegalArgumentException;
    public Boolean deleteTask(long id,String username);
    public Task updateTask(long id, Task newTaskData);
    public List<Task> getAllTasks(String username);
} 
