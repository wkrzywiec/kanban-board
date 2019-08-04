package com.wkrzywiec.medium.kanban.model;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "Kanban", description = "Kanban")
public class KanbanWithoutId {

    private String title;
}
