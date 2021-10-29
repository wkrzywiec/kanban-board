package com.wkrzywiec.medium.kanban.controller;

import com.wkrzywiec.medium.kanban.model.Kanban;
import com.wkrzywiec.medium.kanban.model.Task;
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
public class KanbanControllerITCase extends CommonITCase {

    private String baseURL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllKanbans_thenReceiveSingleKanban(){

        //given
        saveSingleRandomKanban();

        //when
        ResponseEntity<List<Kanban>> response = this.restTemplate.exchange(
                                                baseURL + "kanbans/",
                                                    HttpMethod.GET,
                                                    new HttpEntity<>(new HttpHeaders()),
                                                    new ParameterizedTypeReference<List<Kanban>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().size() >= 1);
    }

    @Test
    public void whenGetSingleKanbanById_thenReceiveSingleKanban(){

        //given
        Kanban kanban = saveSingleRandomKanban();

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(kanban.getId(), response.getBody().getId());
        assertEquals(kanban.getTitle(), response.getBody().getTitle());
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
        assertEquals(kanban.getTasks().get(0).getId(), response.getBody().get(0).getId());
        assertEquals(kanban.getTasks().get(0).getTitle(), response.getBody().get(0).getTitle());
        assertEquals(kanban.getTasks().get(0).getDescription(), response.getBody().get(0).getDescription());
        assertEquals(kanban.getTasks().get(0).getColor(), response.getBody().get(0).getColor());
    }

    @Test
    public void whenGetSingleKanbanByTitle_thenReceiveSingleKanban(){

        //given
        Kanban kanban = saveSingleRandomKanban();

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans?title=" + kanban.getTitle(),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(kanban.getId(), response.getBody().getId());
        assertEquals(kanban.getTitle(), response.getBody().getTitle());
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

            // check response Kanban
        Kanban responseKanban = response.getBody();
        assertNotNull(responseKanban.getId());
        assertEquals(kanban.getTitle(), responseKanban.getTitle());

            // check Kanban saved in db
        Kanban savedKanban = findKanbanInDbById(responseKanban.getId()).get();
        assertEquals(kanban.getTitle(), savedKanban.getTitle());
    }

    @Test
    public void whenPostSingleTaskToAlreadyCreatedKanban_thenItIsStoredInDbAndAssignedToKanban(){

        //given
        Kanban kanban = saveSingleRandomKanban();
        Task task = createSingleTask();

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId() + "/tasks/",
                HttpMethod.POST,
                new HttpEntity<>(convertTaskToDTO(task), new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // check response Kanban
        Kanban responseKanban = response.getBody();
        assertNotNull(responseKanban.getId());
        assertEquals(kanban.getTitle(), responseKanban.getTitle());
        assertTrue(responseKanban.getTasks().size() == 1);

        Task responseTask = responseKanban.getTasks().get(0);
        // check response Task
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
    public void whenPutSingleKanban_thenItIsUpdated(){

        //given
        Kanban kanban = saveSingleRandomKanban();
        kanban.setTitle(kanban.getTitle() + " Updated");

        //when
        ResponseEntity<Kanban> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(convertKanbanToDTO(kanban), new HttpHeaders()),
                Kanban.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(kanban.getTitle(), findKanbanInDbById(kanban.getId()).get().getTitle());
    }

    @Test
    public void whenDeleteSingleKanbanById_thenItIsDeletedFromDb(){

        //given
        Kanban kanban = saveSingleRandomKanban();

        //when
        ResponseEntity<String> response = this.restTemplate.exchange(
                baseURL + "kanbans/" + kanban.getId(),
                HttpMethod.DELETE,
                new HttpEntity<>(new HttpHeaders()),
                String.class);

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(String.format("Kanban with id: %d was deleted", kanban.getId()), response.getBody());
        assertFalse(findKanbanInDbById(kanban.getId()).isPresent());
    }
}
