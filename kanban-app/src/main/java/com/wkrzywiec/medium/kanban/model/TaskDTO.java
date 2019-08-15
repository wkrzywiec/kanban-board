package com.wkrzywiec.medium.kanban.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    @ApiModelProperty(position = 1)
    private String title;

    @ApiModelProperty(position = 2)
    private String description;

    @ApiModelProperty(position = 3)
    private String color;

    @ApiModelProperty(position = 4)
    private TaskStatus status;
}
