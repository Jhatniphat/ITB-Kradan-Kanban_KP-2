package com.example.kradankanban_backend.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private int id;
    private String title;
    private String assignees;
    private Object status;
}
