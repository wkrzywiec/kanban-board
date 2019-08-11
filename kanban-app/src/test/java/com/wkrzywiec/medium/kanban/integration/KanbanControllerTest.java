package com.wkrzywiec.medium.kanban.integration;

import com.wkrzywiec.medium.kanban.model.Kanban;
import com.wkrzywiec.medium.kanban.model.KanbanDTO;
import com.wkrzywiec.medium.kanban.model.Task;
import com.wkrzywiec.medium.kanban.repository.KanbanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class KanbanControllerTest {

    private String baseURL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private KanbanRepository kanbanRepository;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllKanbans_thenReceiveSingleKanban(){

        //given
        Kanban kanban = saveSingleKanban();

        //when
        ResponseEntity<List<Kanban>> response = this.restTemplate.exchange(
                                                baseURL + "kanbans/",
                                                    HttpMethod.GET,
                                                    new HttpEntity<>(new HttpHeaders()),
                                                    new ParameterizedTypeReference<List<Kanban>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(kanban.getTitle(), response.getBody().get(0).getTitle());
    }

    @Test
    public void whenGetSingleKanbanById_thenReceiveSingleKanban(){

        //given
        Kanban kanban = saveSingleKanban();

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(kanban.getId(), response.getBody().getId());
    }

    @Test
    public void whenGetAllTasksForKanbanById_thenReceiveTasksList(){

        //given
        Kanban kanban = saveSingleKanbanWithOneTask();

        //when
        ResponseEntity<List<Task>> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId() + "/tasks/",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<Task>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(kanban.getTasks().get(0), response.getBody().get(0));
    }

    @Test
    public void whenGetSingleKanbanByTitle_thenReceiveSingleKanban(){

        //given
        Kanban kanban = saveSingleKanban();

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans?title=" + kanban.getTitle(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(kanban.getId(), response.getBody().getId());
    }

    @Test
    public void whenPostSingleKanban_thenItIsStoredInDb(){

        //given
        Kanban kanban = createSingleKanban();

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans/",
                HttpMethod.POST,
                new HttpEntity<>(convertKanbanToDTO(kanban), new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(kanban.getTitle(), findKanbanInDbByTitle(kanban.getTitle()).getTitle());
    }

    @Test
    public void whenPutSingleKanban_thenItIsUpdated(){

        //given
        Kanban kanban = saveSingleKanban();
        kanban.setTitle(kanban.getTitle() + " Updated");

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(convertKanbanToDTO(kanban), new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(kanban.getTitle(), findKanbanInDbByTitle(kanban.getTitle()).getTitle());
    }

    @Test
    public void whenDeleteSingleKanbanById_thenItIsDeletedFromDb(){

        //given
        Kanban kanban = saveSingleKanban();

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(new HttpHeaders()),
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("Kanban with id: %d was deleted", kanban.getId()), response.getBody());
        assertFalse(kanbanRepository.findByTitle(kanban.getTitle()).isPresent());
    }

    private Kanban createSingleKanban(){
        Kanban kanban = new Kanban();
        int random = (int)(Math.random() * 100 + 1);
        kanban.setTitle("Test Kanban " + random);
        return kanban;
    }

    private Kanban saveSingleKanban(){
        return kanbanRepository.save(createSingleKanban());
    }

    private Kanban saveSingleKanbanWithOneTask(){
        Kanban kanban = saveSingleKanban();
        List<Task> taskList = new ArrayList<>();
        taskList.add(createSingleTask());
        kanban.setTasks(taskList);
        return kanbanRepository.save(kanban);
    }

    private Task createSingleTask(){
        Task task = new Task();
        int random = (int)(Math.random() * 100 + 1);
        task.setTitle("Test Task " + random);
        task.setDescription("Description " + random);
        task.setColor("Color " + random);
        return task;
    }
    private Kanban findKanbanInDbByTitle(String title) {
        return kanbanRepository.findByTitle(title).get();
    }

    private KanbanDTO convertKanbanToDTO(Kanban kanban) {
        return new KanbanDTO().builder()
                                .title(kanban.getTitle())
                                .build();
    }
}
