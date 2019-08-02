package com.wkrzywiec.medium.kanban.model;

import lombok.Data;

@Data
public class Task {

    private Long id;
    private String title;
    private String description;
    private String color;
}
