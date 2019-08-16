package com.wkrzywiec.medium.kanban.integration;

import com.wkrzywiec.medium.kanban.model.*;
import com.wkrzywiec.medium.kanban.repository.KanbanRepository;
import com.wkrzywiec.medium.kanban.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.Optional;

@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class CommonTest {

    @Autowired
    private KanbanRepository kanbanRepository;

    @Autowired
    private TaskRepository taskRepository;

    protected Kanban createSingleKanban(){
        Kanban kanban = new Kanban();
        int random = (int)(Math.random() * 100 + 1);
        kanban.setTitle("Test Kanban " + random);
        kanban.setTasks(new ArrayList<>());
        return kanban;
    }

    protected Task createSingleTask(){
        Task task = new Task();
        int random = (int)(Math.random() * 100 + 1);
        task.setTitle("Test Task " + random);
        task.setDescription("Description " + random);
        task.setColor("Color " + random);
        task.setStatus(TaskStatus.TODO);
        return task;
    }

    protected KanbanDTO convertKanbanToDTO(Kanban kanban) {
        return new KanbanDTO().builder()
                .title(kanban.getTitle())
                .build();
    }

    protected TaskDTO convertTaskToDTO(Task task) {
        return new TaskDTO().builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .color(task.getColor())
                .status(task.getStatus())
                .build();
    }

    protected Kanban saveSingleRandomKanban(){
        return kanbanRepository.save(createSingleKanban());
    }

    protected Kanban saveSingleKanbanWithOneTask(){
        Kanban kanban = createSingleKanban();
        Task task = createSingleTask();
        kanban.addTask(task);
        return kanbanRepository.save(kanban);
    }

    protected Task saveSingleTask(){
        return taskRepository.save(createSingleTask());
    }

    protected Optional<Kanban> findKanbanInDbById(Long id) {
        return kanbanRepository.findById(id);
    }

    protected Optional<Task> findTaskInDbById(Long id) {
        return taskRepository.findById(id);
    }
}
