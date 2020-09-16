package com.wkrzywiec.medium.kanban.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name ="kanban")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Kanban.class)
public class Kanban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(position = 1)
    private Long id;

    @ApiModelProperty(position = 2)
    private String title;

    @OneToMany(
            cascade = {CascadeType.ALL},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "kanban_id")
    @ApiModelProperty(position = 3)
    private List<Task> tasks;

    public void addTask(Task task) {

        if (Objects.isNull(tasks)) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }
}
