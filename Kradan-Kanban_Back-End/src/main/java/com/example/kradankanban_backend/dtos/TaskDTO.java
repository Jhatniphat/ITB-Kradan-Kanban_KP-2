package com.example.kradankanban_backend.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private int taskId;
    private String taskTitle;
    private String taskAssignees;
    private Object taskStatus;
}
