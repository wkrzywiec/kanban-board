package com.wkrzywiec.medium.kanban.repository;

import com.wkrzywiec.medium.kanban.model.Kanban;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanRepository extends CrudRepository<Kanban, Long> {
}
