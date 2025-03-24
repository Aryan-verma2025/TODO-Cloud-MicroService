package com.example.demo.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Jwt.JwtUtil;
import com.example.demo.Services.TaskService;
import com.example.demo.Services.UserService;
import com.example.demo.Entities.Task;


import java.util.List;


@RestController
public class MainController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;
    
    @GetMapping("/test-jwt")
    public ResponseEntity<String> tst(@RequestHeader("Authorization") String token){

        try{
             token = token.substring(7);
             System.out.println("Auth TOken = "+token);
             Boolean isValid = jwtUtil.validateToken(token);

             if(isValid){
                String username = jwtUtil.extractUsername(token);

                return new ResponseEntity<>("Authorized : "+username,HttpStatus.OK);
             }
        }catch(Exception e){
            return new ResponseEntity<>("Exception Occured",HttpStatus.INTERNAL_SERVER_ERROR);
        }

       

        return new ResponseEntity<>("Un-authorized",HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/create-task")
    public ResponseEntity<String> createTask(@RequestBody Task task){
        task.setUsername(userService.getLoggedInUsername());

        try{
            taskService.createTask(task);
        }catch(IllegalArgumentException exception){
            return new ResponseEntity<>("Bad Request",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Task Created",HttpStatus.CREATED);
        
    }

    @PostMapping("/update-task")
    public ResponseEntity<String> updateTask(@RequestBody Task task){
        task.setUsername(userService.getLoggedInUsername());

        try{
            taskService.updateTask(task.getId(), task);
        }catch(IllegalArgumentException exception){
            return new ResponseEntity<>("Task Not Found",HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>("Task Updated",HttpStatus.OK);
    }

    @PostMapping("/delete-task")
    public ResponseEntity<String> deleteTask(@RequestBody Task task){
        task.setUsername(userService.getLoggedInUsername());
        Boolean isDeleted = taskService.deleteTask(task.getId(), task.getUsername());
        if(!isDeleted){
            return new ResponseEntity<>("Task Not Found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Task Deleted",HttpStatus.OK);
    }

    @GetMapping("/get-all-tasks")
    public List<Task> getAllTasks(){
        return taskService.getAllTasks(userService.getLoggedInUsername());
    }
}
