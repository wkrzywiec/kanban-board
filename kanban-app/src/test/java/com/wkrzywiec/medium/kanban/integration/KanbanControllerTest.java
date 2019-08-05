package com.wkrzywiec.medium.kanban.integration;

import com.wkrzywiec.medium.kanban.model.Kanban;
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

import java.util.List;

import static org.junit.Assert.assertEquals;

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
    KanbanRepository kanbanRepository;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllKanbans_thenRecieveSingle(){

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

    private Kanban saveSingleKanban(){

        Kanban kanban = new Kanban();
        int random = (int)(Math.random() * 100 + 1);
        kanban.setTitle("Test Kanban " + random);
        return kanbanRepository.save(kanban);
    }
}
