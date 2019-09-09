package com.wkrzywiec.medium.kanban.controller;

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

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TaskControllerITCase extends CommonITCase {

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
        assertEquals(task.getStatus(), responseTask.getStatus());

            // check saved Task in db
        Task savedTask = findTaskInDbById(responseTask.getId()).get();
        assertEquals(responseTask.getId(), savedTask.getId());
        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getDescription(), savedTask.getDescription());
        assertEquals(task.getColor(), savedTask.getColor());
        assertEquals(task.getStatus(), savedTask.getStatus());
    }

    @Test
    public void whenPostSingleTaskWithKanbanAssignment_thenItIsStoredInDb(){

        //given
        Task task = createSingleTask();
        saveSingleRandomKanban();

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
        assertEquals(task.getStatus(), responseTask.getStatus());

        // check saved Task in db
        Task savedTask = findTaskInDbById(responseTask.getId()).get();
        assertEquals(responseTask.getId(), savedTask.getId());
        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getDescription(), savedTask.getDescription());
        assertEquals(task.getColor(), savedTask.getColor());
        assertEquals(task.getStatus(), savedTask.getStatus());
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
        assertEquals(task.getTitle(), findTaskInDbById(task.getId()).get().getTitle());
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
        assertFalse(findTaskInDbById(task.getId()).isPresent());
    }
}
