package com.wkrzywiec.medium.kanban.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KanbanWithoutId {

    private String title;

    public static KanbanWithoutId createFromKanban(Kanban kanban) {
        return new KanbanWithoutId(kanban.getTitle());
    }
}
