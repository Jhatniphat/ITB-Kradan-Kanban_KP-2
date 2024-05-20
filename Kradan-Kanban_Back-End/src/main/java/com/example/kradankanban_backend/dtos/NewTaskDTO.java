package com.example.kradankanban_backend.dtos;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class NewTaskDTO {
    @NotNull
    @Max(100)
    private String title;
    @Max(500)
    private String description;
    @Max(30)
    private String assignees;
    private Object status;
}
