package com.wkrzywiec.medium.kanban.service;

import com.wkrzywiec.medium.kanban.model.Kanban;
import com.wkrzywiec.medium.kanban.repository.KanbanRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KanbanServiceTest {

    KanbanService kanbanService;
    @Mock
    KanbanRepository kanbanRepository;

    @Before
    public void init() {
        kanbanService = new KanbanServiceImpl(kanbanRepository);
    }

    @Test
    public void when2KanbansInDatabase_thenGetListWithAllOfThem() {
        //given
        mockKanbanInDatabase(2);

        //when
        List<Kanban> kanbans = kanbanService.getAllKanbanBoards();

        //then
        assertEquals(2, kanbans.size());
    }

    private void mockKanbanInDatabase(int kanbanCount) {
        when(kanbanRepository.findAll())
                .thenReturn(createKanbanList(kanbanCount));
    }

    private List<Kanban> createKanbanList(int kanbanCount) {
        List<Kanban> kanbans = new ArrayList<>();
        IntStream.range(0, kanbanCount)
                .forEach(number ->{
                    Kanban kanban = new Kanban();
                    kanban.setId(Long.valueOf(number));
                    kanban.setTitle("Kanban " + number);
                    kanban.setTasks(new ArrayList<>());
                    kanbans.add(kanban);
                });
        return kanbans;
    }
}
