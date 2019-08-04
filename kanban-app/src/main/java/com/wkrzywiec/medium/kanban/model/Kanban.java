package com.wkrzywiec.medium.kanban.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name ="kanban")
public class Kanban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(position = 1)
    private Long id;

    @Column(name = "title")
    @ApiModelProperty(position = 2)
    private String title;

    @OneToMany
    @JoinColumn(name = "kanban_id")
    @ApiModelProperty(position = 3)
    private List<Task> tasks;
}
