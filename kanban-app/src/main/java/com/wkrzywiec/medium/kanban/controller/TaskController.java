package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Task;
import com.wkrzywiec.medium.kanban.model.TaskWithoutId;
import com.wkrzywiec.medium.kanban.repository.TaskRepository;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskRepository taskRepository;

    @GetMapping("/")
    @ApiOperation(value="View a list of all tasks", response = Task.class, responseContainer = "List")
    public ResponseEntity<?> getAllTasks(){
        try {
            List<Task> tasksList = new ArrayList<>();
            taskRepository.findAll().forEach(tasksList::add);
            return new ResponseEntity<>(tasksList, HttpStatus.OK);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value="Find a task info by its id", response = Task.class)
    public ResponseEntity<?> getTask(@PathVariable Long id){
        try {
            Optional<Task> optTask = taskRepository.findById(id);
            if (optTask.isPresent()) {
                return new ResponseEntity<>(optTask.get(), HttpStatus.OK);
            } else {
                return noTaskFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createTask(@RequestBody TaskWithoutId taskWithoutId){
        try {
            Task task = new Task();
            task.setTitle(taskWithoutId.getTitle());
            task.setDescription(taskWithoutId.getDescription());
            task.setColor(taskWithoutId.getColor());
            return new ResponseEntity<>(taskRepository.save(task), HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/")
    public ResponseEntity<?> updateTask(@RequestBody Task task){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(){
        return null;
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something went wrong :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noTaskFoundResponse(Long id){
        return new ResponseEntity<>("No task found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
