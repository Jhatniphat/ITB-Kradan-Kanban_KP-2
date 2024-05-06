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

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", this.title);
        map.put("description", this.description);
        map.put("assignees", this.assignees);
        map.put("status", this.status);
        return map;
    }
}
