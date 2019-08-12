package com.wkrzywiec.medium.kanban.integration;

import com.wkrzywiec.medium.kanban.model.Task;
import com.wkrzywiec.medium.kanban.model.TaskDTO;
import com.wkrzywiec.medium.kanban.repository.TaskRepository;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class TaskControllerTest {

    private String baseURL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TaskRepository taskRepository;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllTasks_thenReceiveSingleTask(){

        //given
        Task task = saveSingleTask();

        //when
        ResponseEntity<List<Task>> response = this.restTemplate.exchange(
                baseURL + "tasks/",
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                new ParameterizedTypeReference<List<Task>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() >= 1);
    }

    @Test
    public void whenGetSingleTaskById_thenReceiveSingleTask(){

        //given
        Task task = saveSingleTask();

        //when
        ResponseEntity<Task> response = this.restTemplate.exchange(
                baseURL + "tasks/" + task.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Task.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task, response.getBody());
    }

    @Test
    public void whenPostSingleTask_thenItIsStoredInDb(){

        //given
        Task task = createSingleTask();

        //when
        ResponseEntity<Task> response = this.restTemplate.exchange(
                baseURL + "tasks/",
                HttpMethod.POST,
                new HttpEntity<>(convertTaskToDTO(task), new HttpHeaders()),
                Task.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

            // check response Task
        Task responseTask = response.getBody();
        assertNotNull(responseTask.getId());
        assertEquals(task.getTitle(), responseTask.getTitle());
        assertEquals(task.getDescription(), responseTask.getDescription());
        assertEquals(task.getColor(), responseTask.getColor());

            // check saved Task in db
        Task savedTask = taskRepository.findById(responseTask.getId()).get();
        assertEquals(responseTask.getId(), savedTask.getId());
        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getDescription(), savedTask.getDescription());
        assertEquals(task.getColor(), savedTask.getColor());

    }

    @Test
    public void whenPutSingleTask_thenItIsUpdated(){

        //given
        Task task = saveSingleTask();
        task.setTitle(task.getTitle() + " Updated");

        //when
        ResponseEntity<Task> response = this.restTemplate.exchange(
                baseURL + "tasks/" + task.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(convertTaskToDTO(task), new HttpHeaders()),
                Task.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(task.getTitle(), taskRepository.findById(task.getId()).get().getTitle());
    }

    @Test
    public void whenDeleteSingleTaskById_thenItIsDeletedFromDb(){

        //given
        Task task = saveSingleTask();

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(
                baseURL + "tasks/" + task.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(new HttpHeaders()),
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("Task with id: %d was deleted", task.getId()), response.getBody());
        assertFalse(taskRepository.findById(task.getId()).isPresent());
    }

    private Task createSingleTask(){
        Task task = new Task();
        int random = (int)(Math.random() * 100 + 1);
        task.setTitle("Test Task " + random);
        task.setDescription("Description " + random);
        task.setColor("Color " + random);
        return task;
    }

    private TaskDTO convertTaskToDTO(Task task) {
        return new TaskDTO().builder()
                            .title(task.getTitle())
                            .description(task.getDescription())
                            .color(task.getColor())
                            .build();
    }

    private Task saveSingleTask(){
        return taskRepository.save(createSingleTask());
    }
}
