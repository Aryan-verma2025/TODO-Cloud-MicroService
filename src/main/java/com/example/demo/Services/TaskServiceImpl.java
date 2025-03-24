package com.example.demo.Services;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.Entities.Task;
import com.example.demo.Repository.TaskRepo;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService{
    
    @Autowired
    private TaskRepo taskRepo;

    @Override
    public Task createTask(Task task)throws IllegalArgumentException{
       return taskRepo.save(task);

    }

    @Override
    public Boolean deleteTask(long id,String username){
        try{
            Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id " + id));
    
            if (!task.getUsername().equals(username)) {
                throw new IllegalArgumentException("Username mismatch! Cannot delete task.");
            }
            taskRepo.deleteById(id);
        }catch(IllegalArgumentException exception){
            return false;
        }
        return true;
    }

    @Override
    public Task updateTask(long id, Task newTaskData) {
        Task task = taskRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with id " + id));
    
        if (!task.getUsername().equals(newTaskData.getUsername())) {
            throw new IllegalArgumentException("Username mismatch! Cannot update task.");
        }
    
        task.setName(newTaskData.getName());
        task.setDescription(newTaskData.getDescription());
        task.setStatus(newTaskData.getStatus());
        task.setPriority(newTaskData.getPriority());
        task.setDueDate(newTaskData.getDueDate());
    
        return taskRepo.save(task);
    }

    public List<Task> getAllTasks(String username){
        return taskRepo.getAllTasks(username);
    }
    
}
