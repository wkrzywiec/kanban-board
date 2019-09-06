package com.wkrzywiec.medium.kanban.integration.service;

import com.wkrzywiec.medium.kanban.model.Kanban;
import com.wkrzywiec.medium.kanban.model.KanbanDTO;
import com.wkrzywiec.medium.kanban.repository.KanbanRepository;
import com.wkrzywiec.medium.kanban.service.KanbanService;
import com.wkrzywiec.medium.kanban.service.KanbanServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class KanbanServiceTest {

    @Autowired
    private KanbanRepository kanbanRepository;
    private KanbanService kanbanService;


    @Before
    public void init() {
        kanbanService = new KanbanServiceImpl(kanbanRepository);
    }


    @Test
    public void whenNewKanbanCreated_thenKanbanIsSavedInDb() {
        //given
        KanbanDTO kanbanDTO = KanbanDTO.builder()
                                    .title("Test Kanban")
                                .build();

        //when
        kanbanService.saveNewKanban(kanbanDTO);

        //then
        List<Kanban> kanbans = (List<Kanban>) kanbanRepository.findAll();

        assertNotNull(kanbans.get(0));
        assertEquals("Test Kanban", kanbans.get(0).getTitle());
    }
}
