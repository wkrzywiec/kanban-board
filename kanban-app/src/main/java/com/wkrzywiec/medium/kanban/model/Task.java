package com.wkrzywiec.medium.kanban.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Data
@Entity
@NoArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(position = 1)
    private Long id;

    @Column(name = "title")
    @ApiModelProperty(position = 2)
    private String title;

    @Column(name = "description")
    @ApiModelProperty(position = 3)
    private String description;

    @Column(name = "color")
    @ApiModelProperty(position = 4)
    private String color;
}
