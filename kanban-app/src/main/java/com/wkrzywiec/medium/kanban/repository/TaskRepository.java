package com.wkrzywiec.medium.kanban.repository;

import com.wkrzywiec.medium.kanban.model.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
}
