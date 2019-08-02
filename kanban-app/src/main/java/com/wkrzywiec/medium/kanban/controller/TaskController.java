package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Task;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping("/")
    public ResponseEntity<?> getAllTasks(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTask(@PathVariable Long id){
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> createTask(@RequestBody Task task){
        return null;
    }

    @PutMapping("/")
    public ResponseEntity<?> updateTask(@RequestBody Task task){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(){
        return null;
    }
}
