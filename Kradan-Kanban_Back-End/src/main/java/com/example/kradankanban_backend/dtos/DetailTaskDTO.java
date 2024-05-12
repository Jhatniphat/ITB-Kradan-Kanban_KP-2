package com.example.kradankanban_backend.dtos;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class DetailTaskDTO {
    private int id;
    private String title;
    private String description;
    private String assignees;
    private Object status;
}
