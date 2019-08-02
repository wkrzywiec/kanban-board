package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Kanban;
import com.wkrzywiec.medium.kanban.repository.KanbanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kanbans")
@RequiredArgsConstructor
public class KanbanController {

    private final KanbanRepository kanbanRepository;

    @GetMapping("/")
    public ResponseEntity<List<Kanban>> getAllKanbans(){
        List<Kanban> kanbanList = new ArrayList<>();
        kanbanRepository.findAll().forEach(kanbanList::add);
        return new ResponseEntity<>(kanbanList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKanban(@PathVariable Long id){
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<Kanban> createKanban(@RequestBody Kanban kanban){
        return new ResponseEntity<>(kanbanRepository.save(kanban), HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<?> updateKanban(@RequestBody Kanban kanban){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKanban(){
        return null;
    }

    @GetMapping("/{kanbanId}/tasks/")
    public ResponseEntity<?> getAllTasksInKanban(@PathVariable Long kanbanId){
        return null;
    }
}
