package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Kanban;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kaban")
public class KanbanController {


    @GetMapping("/")
    public ResponseEntity<?> getAllKanbans(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getKanban(@PathVariable Long id){
        return null;
    }

    @PostMapping("/")
    public ResponseEntity<?> createKanban(@RequestBody Kanban kanban){
        return null;
    }

    @PutMapping("/")
    public ResponseEntity<?> updateKanban(@RequestBody Kanban kanban){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKanban(){
        return null;
    }
}
