package com.wkrzywiec.medium.kanban.integration;

import com.wkrzywiec.medium.kanban.model.Kanban;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DataJpaTest
public class KanbanControllerTest {

    //https://www.baeldung.com/spring-boot-testing

    private String baseURL;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TestEntityManager entityManager;

    @Before
    public void setUp(){
        baseURL = "http://localhost:" + port;
    }

    @Test
    public void whenGetAllKanbans_thenRecieveSingle(){

        //given
        Kanban kanban = new Kanban();
        kanban.setTitle("Test Kanban");
        this.entityManager.persist(kanban);
        this.entityManager.flush();

        //when
        ResponseEntity<List<Kanban>> response = this.restTemplate.exchange(
                                                baseURL + "kanbans/",
                                                    HttpMethod.GET,
                                                    new HttpEntity<>(new HttpHeaders()),
                                                    new ParameterizedTypeReference<List<Kanban>>() {});

        //then
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}
