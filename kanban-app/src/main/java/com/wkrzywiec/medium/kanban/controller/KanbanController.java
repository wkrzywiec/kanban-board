package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Kanban;
import com.wkrzywiec.medium.kanban.model.KanbanDTO;
import com.wkrzywiec.medium.kanban.model.Task;
import com.wkrzywiec.medium.kanban.model.TaskDTO;
import com.wkrzywiec.medium.kanban.service.KanbanService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/kanbans")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class KanbanController {

    private final KanbanService kanbanService;

    @GetMapping("/")
    @ApiOperation(value="View a list of all Kanban boards", response = Kanban.class, responseContainer = "List")
    public ResponseEntity<?> getAllKanbans(){
        try {
            return new ResponseEntity<>(
                    kanbanService.getAllKanbanBoards(),
                    HttpStatus.OK);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value="Find a Kanban board info by its id", response = Kanban.class)
    public ResponseEntity<?> getKanban(@PathVariable Long id){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(id);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        optKanban.get(),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("")
    @ApiOperation(value="Find a Kanban board info by its title", response = Kanban.class)
    public ResponseEntity<?> getKanbanByTitle(@RequestParam String title){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanByTitle(title);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        optKanban.get(),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No kanban found with a title: " + title, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/")
    @ApiOperation(value="Save new Kanban board", response = Kanban.class)
    public ResponseEntity<?> createKanban(@RequestBody KanbanDTO kanbanDTO){
        try {
            return new ResponseEntity<>(
                    kanbanService.saveNewKanban(kanbanDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Update a Kanban board with specific id", response = Kanban.class)
    public ResponseEntity<?> updateKanban(@PathVariable Long id, @RequestBody KanbanDTO kanbanDTO){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(id);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        kanbanService.updateKanban(optKanban.get(), kanbanDTO),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value="Delete Kanban board with specific id", response = String.class)
    public ResponseEntity<?> deleteKanban(@PathVariable Long id){
        try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(id);
            if (optKanban.isPresent()) {
                kanbanService.deleteKanban(optKanban.get());
                return new ResponseEntity<>(
                        String.format("Kanban with id: %d was deleted", id),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(id);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @GetMapping("/{kanbanId}/tasks/")
    @ApiOperation(value="View a list of all tasks for a Kanban with provided id", response = Task.class, responseContainer = "List")
    public ResponseEntity<?> getAllTasksInKanban(@PathVariable Long kanbanId){
         try {
            Optional<Kanban> optKanban = kanbanService.getKanbanById(kanbanId);
            if (optKanban.isPresent()) {
                return new ResponseEntity<>(
                        optKanban.get().getTasks(),
                        HttpStatus.OK);
            } else {
                return noKanbanFoundResponse(kanbanId);
            }
        } catch (Exception e) {
            return errorResponse();
        }
    }

    @PostMapping("/{kanbanId}/tasks/")
    @ApiOperation(value="Save new Task and assign it to Kanban board", response = Kanban.class)
    public ResponseEntity<?> createTaskAssignedToKanban(@PathVariable Long kanbanId, @RequestBody TaskDTO taskDTO){
        try {
            return new ResponseEntity<>(
                    kanbanService.addNewTaskToKanban(kanbanId, taskDTO),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            return errorResponse();
        }
    }

    private ResponseEntity<String> errorResponse(){
        return new ResponseEntity<>("Something went wrong :(", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> noKanbanFoundResponse(Long id){
        return new ResponseEntity<>("No kanban found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
