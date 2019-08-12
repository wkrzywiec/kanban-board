package com.wkrzywiec.medium.kanban;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.wkrzywiec.medium.kanban.model")
@EnableJpaRepositories("com.wkrzywiec.medium.kanban.repository")
public class KanbanApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanApplication.class, args);
	}

}
